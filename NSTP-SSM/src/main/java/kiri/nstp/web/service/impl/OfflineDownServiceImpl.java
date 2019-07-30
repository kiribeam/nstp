package kiri.nstp.web.service.impl;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kiri.nstp.aop.PGPSignAndEncrypt;
import kiri.nstp.aop.ResourcePermission;
import kiri.nstp.dao.EcuKeyChangeDao;
import kiri.nstp.dao.EcuKeyDao;
import kiri.nstp.dao.OfflineLogDao;
import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.exception.CryptoAlgorithmGetException;
import kiri.nstp.exception.NoResourcePermissionException;
import kiri.nstp.pojo.EcuKey;
import kiri.nstp.pojo.EcuKeyChange;
import kiri.nstp.pojo.OfflineLog;
import kiri.nstp.security.OfflineDownUtils;
import kiri.nstp.util.EcuKeyChangeFactory;
import kiri.nstp.util.EcuSearchMessageFactory;
import kiri.nstp.util.OfflineLogFactory;
import kiri.nstp.web.service.OfflineDownService;
import kiri.utils.HexUtils;

@Service
public class OfflineDownServiceImpl implements OfflineDownService {
	
	@Autowired
	private OfflineLogDao offlineLogDao;
	@Autowired
	private EcuKeyDao ecuKeyDao;
	@Autowired
	private EcuKeyChangeDao keyChangeDao;

	@Autowired
	private EcuKeyChangeFactory keyChangeFac;
	@Autowired
	private OfflineLogFactory logFac;
	@Autowired
	private EcuSearchMessageFactory esmFac;
	
	@Value("${row5}")
	private Integer row;
	@Value("${row10}")
	private Integer logRow;

	@Value("${offlineDownFilenameSuffix}")
	private String filenameSuffix;

	@Value("${statusEcuKeyChanging}")
	private Integer ECU_KEY_CHANGING;
	@Value("${statusEcuKeyVerified}")
	private Integer ECU_KEY_VERIFIED;
	@Value("${operationOfflineApp}")
	private Integer OFFLINE_LOG_APP;
	@Value("${operationOfflineVerify}")
	private Integer OFFLINE_LOG_VERIFY;

	@Value("${KEY_UPDATE_ENC_C1}")
	private String KEY_UPDATE_ENC_C1;
	@Value("${KEY_UPDATE_ENC_C2}")
	private String KEY_UPDATE_ENC_C2;
	@Value("${KEY_UPDATE_MAC_C1}")
	private String KEY_UPDATE_MAC_C1;
	@Value("${KEY_UPDATE_MAC_C2}")
	private String KEY_UPDATE_MAC_C2;
	
	@Value("${pgpPkUploadDir}")
	private String pgpPkUploadDir;
	@Value("${pgpPkUploadFileSuffix}")
	private String pgpPkUploadFileSuffix;
	@Value("${serverPgpPrivateKey}")
	private String serverPrivateKey;
	@Value("${serverPgpPrivateKeyPass}")
	private String serverPgpPrivateKeyPass;


	@Override
	@ResourcePermission(permission="readOfflineLog")
	public List<OfflineLog> getLog(EcuSearchMessage esm) {
		PageHelper.startPage(esm.getPageNumber(), logRow);
		return offlineLogDao.getLog(esm);
		
	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	@PGPSignAndEncrypt
	@ResourcePermission(permission="changeEcuKey")
	public byte[] registKey(EcuSearchMessage esm, List<Integer> list,
			Integer authId, Integer keyId, String keyFlag, String inputKey) {
		
		//get ecu keys then get ecu set to regist
		List<EcuKey> keyList = ecuKeyDao.getEcuKeysByList(list);
		Set<String> ecuidSet = new HashSet<>();
		for(EcuKey k: keyList) {
			ecuidSet.add(k.getEcuid());
		}
		byte[] result = new byte[keyList.size() * 64];
		int i = 0;
		for(String ecuid : ecuidSet) {
			byte[] buffer = null;
			esm.setEcuid(ecuid);
			if(inputKey != null && !inputKey.equals("")) {
				byte[] keyBytes = HexUtils.hexStringToByteArray(inputKey);
				buffer = changeKeyApp(esm, authId, keyId, keyFlag, keyBytes);
			}else {
				byte[] keyBytes = new byte[16];
				try {
					SecureRandom random = SecureRandom.getInstance("NativePRNGNonBlocking");
					random.nextBytes(keyBytes);
				}catch(Exception e) {
					e.printStackTrace();
					throw new CryptoAlgorithmGetException();
				}
				buffer = changeKeyApp(esm, authId, keyId, keyFlag, keyBytes);
			}
			System.arraycopy(buffer, 0, result, i*64, buffer.length);
			i++;
		}

		return result;
	}

	private byte[] changeKeyApp(EcuSearchMessage esm, Integer authId, Integer keyId
			, String keyFlag, byte[] keyBytes) {

		esm.setKeyId(authId);
		List<EcuKey> el = ecuKeyDao.getEcuKeys(esm);
		if(el == null || el.isEmpty())
			throw new NoResourcePermissionException();
		EcuKey ek = el.get(0);
		byte[] authKey = HexUtils.hexStringToByteArray(ek.getKeyValue());

		//here no need to test permission twice
		esm = esmFac.genBlank();
		esm.setEcuid(ek.getEcuid());
		esm.setKeyId(keyId);
		el = ecuKeyDao.getEcuKeys(esm);
		if(el == null || el.isEmpty())
			throw new NoResourcePermissionException();
		ek = el.get(0);

		byte[]keyUpEnc = keyId>10 ? 
				HexUtils.hexStringToByteArray(KEY_UPDATE_ENC_C2)
				: HexUtils.hexStringToByteArray(KEY_UPDATE_ENC_C1);
		byte[]keyUpMac = keyId>10 ? 
				HexUtils.hexStringToByteArray(KEY_UPDATE_MAC_C2)
				:HexUtils.hexStringToByteArray(KEY_UPDATE_MAC_C1);
		byte[] message = OfflineDownUtils.generateM123(authId, keyId,
				ek.getCount(), keyFlag, keyBytes, authKey, 
				keyUpEnc, keyUpMac);
		//System.out.println(HexUtils.byteArrayToHexString(message));
		String m4 = HexUtils.byteArrayToHexString(OfflineDownUtils.generateM4(ek.getCount(),
				keyBytes, keyUpEnc));

		ek.setCount(ek.getCount()+1);
		//generate file
		String username = SecurityUtils.getSubject().getPrincipal().toString();
		EcuKeyChange ekc = keyChangeFac.gen(ek.getEcuid(), keyId, 
				ek.getCount(), username, HexUtils.byteArrayToHexString(keyBytes), 
				m4, keyFlag);
		keyChangeDao.add(ekc);
		
		ek.setStatus(ECU_KEY_CHANGING);
		ek.setKeyFlag(null);
		ek.setKeyValue(null);
		ecuKeyDao.updateEcuKey(ek);

		OfflineLog log = logFac.gen(ek.getEcuid(), keyId, 
				OFFLINE_LOG_APP, username, keyFlag);
		offlineLogDao.addLog(log);
		
		return message;
	}
	
	@Transactional(rollbackFor= {Exception.class})
	@Override
	@ResourcePermission(permission="verifyEcuKey")
	public void verifyKey(EcuSearchMessage esm) {
		List<EcuKeyChange> el = keyChangeDao.getList(esm);
		if(el == null || el.isEmpty())
			throw new NoResourcePermissionException();
		EcuKeyChange ekc = el.get(0);

		//Here caution, if count < ecukey.count then status will not set 
		esm = esmFac.genBlank();
		esm.setEcuid(ekc.getEcuid());
		esm.setKeyId(ekc.getKeyId());

		EcuKey ecuKey = ecuKeyDao.getEcuKeys(esm).get(0);
		ecuKey.setStatus(null);
		if(ecuKey.getCount()<=ekc.getCount()) ecuKey.setStatus(ECU_KEY_VERIFIED);
		ecuKey.setCount(null);
		ecuKey.setKeyFlag(ekc.getKeyFlag());
		ecuKey.setKeyValue(ekc.getOpValue());
		ecuKeyDao.updateEcuKey(ecuKey);

		String username = SecurityUtils.getSubject().getPrincipal().toString();
		OfflineLog log = logFac.gen(ekc.getEcuid(), ekc.getKeyId(),
				OFFLINE_LOG_VERIFY, username, ekc.getKeyFlag());
		offlineLogDao.addLog(log);

		//Here it will delete ecu_key_change if count is lower then current
		keyChangeDao.del(ekc);
	}
}
