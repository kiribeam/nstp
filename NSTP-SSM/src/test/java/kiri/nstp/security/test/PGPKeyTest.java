package kiri.nstp.security.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Iterator;

import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.bc.BcPGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.bc.BcPGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.PGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.PublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;
import org.junit.jupiter.api.Test;

import kiri.utils.HexUtils;

public class PGPKeyTest {
	@Test
	public void run() throws Exception{
		FileInputStream fis = new FileInputStream("/root/iroiro/pgp/test.asc");
		InputStream is = PGPUtil.getDecoderStream(fis);
		PGPPublicKeyRingCollection pgpPub = new BcPGPPublicKeyRingCollection(is);
		PGPPublicKey pubkey = null;
		Iterator<PGPPublicKeyRing> rIt = pgpPub.getKeyRings();
		while(pubkey == null && rIt.hasNext()) {
			PGPPublicKeyRing kRing = rIt.next();
			Iterator<PGPPublicKey> kIt = kRing.getPublicKeys();
			while(pubkey == null && kIt.hasNext()) {
				PGPPublicKey k = kIt.next();
				if(k.isEncryptionKey())
					pubkey = k;
			}
		}
		if(pubkey == null) throw new IllegalArgumentException("Can't find pk");
		System.out.println("fp:" + HexUtils.byteArrayToHexString(pubkey.getFingerprint()));
		Iterator<String> uIt = pubkey.getUserIDs();
		while(uIt.hasNext()) {
			System.out.println(uIt.next());
		}
		is.close();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
	/*	
		
		byte[] keyBytes = pubkey.getEncoded();
		System.out.println("keyBytes:" + keyBytes.length + ":"
				+ HexUtils.byteArrayToHexString(keyBytes));

		int alg = pubkey.getAlgorithm();
		Date time = pubkey.getCreationTime();
		PublicKeyPacket pkp = pubkey.getPublicKeyPacket();
		System.out.println("pkp:"+HexUtils.byteArrayToHexString(pkp.getEncoded()));
		BCPGKey rawkey = pkp.getKey();
		System.out.println("alg:"+alg + " :" + time);
		System.out.println("rawkey:" + HexUtils.byteArrayToHexString(rawkey.getEncoded()));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		
		BCPGInputStream in = new BCPGInputStream(new ByteArrayInputStream(rawkey.getEncoded()));
		BCPGKey remade = new RSAPublicBCPGKey(in);
		PublicKeyPacket packet2 = new PublicKeyPacket(alg, time, remade);
		System.out.println("remade:"+ HexUtils.byteArrayToHexString(packet2.getEncoded()));
		*/
		
		PGPSecretKey seckey = null;
		PGPSecretKeyRingCollection pgpSec = new BcPGPSecretKeyRingCollection(
				PGPUtil.getDecoderStream(new FileInputStream("/root/iroiro/pgp/server.pri")));
		Iterator<PGPSecretKeyRing> keyRingIter = pgpSec.getKeyRings();
		while(seckey == null && keyRingIter.hasNext()){
			PGPSecretKeyRing keyRing = keyRingIter.next();
			Iterator<PGPSecretKey> keyIter = keyRing.getSecretKeys();
			while(seckey == null && keyIter.hasNext()) {
				PGPSecretKey k= keyIter.next();
				if(k.isSigningKey()) {
					seckey = k;
				}
			}
		}
		if(seckey == null) throw new IllegalArgumentException("Can't find prikey");
		Iterator<String> uIt2 = seckey.getUserIDs();
		while(uIt2.hasNext()) {
			System.out.println(uIt2.next());
		}
		PGPPrivateKey prikey = null;
		PBESecretKeyDecryptor pkd = new BcPBESecretKeyDecryptorBuilder(
				new BcPGPDigestCalculatorProvider()).build("kiri@nstp".toCharArray());
		prikey = seckey.extractPrivateKey(pkd);
		System.out.println(prikey.getKeyID());
		System.out.println("~~~~~~~~~~~~~~~~");
		PGPSignatureGenerator sGen = new PGPSignatureGenerator(
				new BcPGPContentSignerBuilder(pubkey.getAlgorithm(),
						PGPUtil.SHA256));
		sGen.init(PGPSignature.BINARY_DOCUMENT,prikey);
		Iterator<String> it = pubkey.getUserIDs();
		if(it.hasNext()) {
			PGPSignatureSubpacketGenerator spGen = new PGPSignatureSubpacketGenerator();
			spGen.setSignerUserID(false, it.next());
			sGen.setHashedSubpackets(spGen.generate());
		}
		PGPCompressedDataGenerator cGen = new PGPCompressedDataGenerator(PGPCompressedData.ZLIB);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BCPGOutputStream bOut = new BCPGOutputStream(cGen.open(out));
		sGen.generateOnePassVersion(false).encode(bOut);
		
		PGPLiteralDataGenerator lGen = new PGPLiteralDataGenerator();
		File file = new File("/root/iroiro/pgp/testfile");
		FileInputStream tmpfis = new FileInputStream(file);
		byte[] targetFile = tmpfis.readAllBytes();
		tmpfis.close();
		OutputStream lOut = //= lGen.open(bOut, PGPLiteralData.BINARY, file);
				lGen.open(bOut, PGPLiteralData.BINARY, "testfile", targetFile.length, new Date());
		fis = new FileInputStream(file);
		int ch;
		while((ch=fis.read())>=0) {
			lOut.write(ch);
			sGen.update((byte)ch);
		}
		lGen.close();
		sGen.generate().encode(bOut);
		bOut.close();
		fis.close();
		byte[] bytes = out.toByteArray();
		PGPDataEncryptorBuilder builder = new BcPGPDataEncryptorBuilder(PGPEncryptedData.AES_256)
				.setWithIntegrityPacket(true).setSecureRandom(new SecureRandom());
		PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(builder);
		PublicKeyKeyEncryptionMethodGenerator gen = new BcPublicKeyKeyEncryptionMethodGenerator(
				pubkey).setSecureRandom(new SecureRandom());
		cPk.addMethod(gen);
		FileOutputStream fos = new FileOutputStream("/root/iroiro/pgp/enAndSigned");
		OutputStream cOut = cPk.open(fos, bytes.length);
		cOut.write(bytes);
		cOut.flush();
		cOut.close();
		fos.close();
		

	}
}
