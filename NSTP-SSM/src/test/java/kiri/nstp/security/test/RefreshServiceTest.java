package kiri.nstp.security.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import kiri.nstp.security.CryptoUtils;
import kiri.nstp.security.RefreshUtils;
import kiri.utils.HexUtils;

public class RefreshServiceTest {
	@Test
	public void doTest() throws Exception{
		byte[] file;
		FileInputStream fis = new FileInputStream("/root/iroiro/test/AppFile.bin");
		file = fis.readAllBytes();
		fis.close();
		String ecuid = "11112222333344445555666677778888";
		byte[] fileHeader = HexUtils.hexStringToByteArray("0301");
		byte[] head = HexUtils.hexStringToByteArray(
				"0001800046574D5F50475541"
		+ ecuid 
		+ "000100010001800000090000");
		byte[] totalMd = CryptoUtils.doSHA256(file);
		KeyPair keyPair = CryptoUtils.doGenerateRSAKeyPair();
		RSAPublicKey dynamicPubKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey dynamicPriKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPrivateKey serverPriKey = RefreshUtils.getPrivateKey("/root/iroiro/test/base_userkey.der");
		byte[] signInfo = RefreshUtils.generateSignerInfo(serverPriKey, 
				"426F6479000000000000000000000000" + "00000000000000060000", dynamicPubKey);
		LinkedList<byte[]> fileList = new LinkedList<>();
		fileList.add(head);
		fileList.add(totalMd);
		fileList.add(signInfo);
		byte[] totalSign = CryptoUtils.doSignBySHA256withRSA(fileList, dynamicPriKey);
		fileList.add(totalSign);
		fileList.add(file);
		fileList.addFirst(fileHeader);
		byte[] total = HexUtils.mergeByteArrays(fileList);
		FileOutputStream fos = new FileOutputStream("/root/iroiro/test/RefreshCus");
		fos.write(total);
		fos.flush();
		fos.close();
	}

}
