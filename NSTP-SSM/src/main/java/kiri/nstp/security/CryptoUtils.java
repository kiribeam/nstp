package kiri.nstp.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.params.KeyParameter;
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

import kiri.nstp.exception.CryptoAlgorithmGetException;
import kiri.nstp.exception.WrongPgpFileException;
import kiri.utils.HexUtils;

public class CryptoUtils {
	static {
		if(Security.getProvider("BC") == null) {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
	}
	
	public static String doCmac(String hexInput, String hexKey) {
		byte[] data = HexUtils.hexStringToByteArray(hexInput);
		byte[] key = HexUtils.hexStringToByteArray(hexKey);
		byte[] result = doCmac(data, key);
		return HexUtils.byteArrayToHexString(result);
	}
	
	public static byte[] doCmac(byte[] data, byte[] byteKey) {
		BlockCipher cip = new AESEngine();
		CMac cmac = new CMac(cip, 128);
		KeyParameter keyp = new KeyParameter(byteKey);
		cmac.init(keyp);
		cmac.update(data, 0, data.length);
		byte[] result = new byte[16];
		cmac.doFinal(result, 0);	
		return result;
	}
	
	public static byte[] doAESCBC128(byte[] data, byte[] byteKey, byte[] iv){
		Cipher cip;
		try{
			cip = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			Key key = new SecretKeySpec(byteKey, "AES");
			cip.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
			byte[] result = cip.doFinal(data);
			return result;
		}catch(Exception e) {
			throw new CryptoAlgorithmGetException();
		}
	}
	public static byte[] doAESECB128(byte[] data, byte[] byteKey) {
		Cipher cip;
		try{
			cip = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
			Key key = new SecretKeySpec(byteKey, "AES");
			cip.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cip.doFinal(data);
			return result;
		}catch(Exception e) {
			throw new CryptoAlgorithmGetException();
		}
	}
	public static byte[] doKDF(byte[] k, byte[] c) {
		byte[] out0 = new byte[16];
		byte[] out1 = kdfCycle(k, out0);
		byte[] result = kdfCycle(c, out1);
		return result;
	}
	private static byte[] kdfCycle(byte[] arg, byte[] out) {
		byte[] result = new byte[16];
		System.arraycopy(doAESECB128(arg, out), 0, result, 0, 16);
		XORBytes(result, arg);
		XORBytes(result, out);
		return result;
	}
	private static void XORBytes(byte[] target, byte[] nochange) {
		for(int i=0; i<target.length; i++) {
			target[i] = (byte)((target[i] & 0xff) ^ (nochange[i] & 0xff));
		}
		
	}
	
	public static byte[] doSHA256(byte[] message) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA256");
		}catch(Exception e) {
			throw new CryptoAlgorithmGetException();
		}
		return md.digest(message);
	}
	
	public static byte[] doSignBySHA256withRSA(byte[] message, byte[] keyByte) {
		PKCS8EncodedKeySpec priSpec = new PKCS8EncodedKeySpec(keyByte);
		try{
			KeyFactory keyFac = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFac.generatePrivate(priSpec);
			return doSignBySHA256withRSA(message, priKey);

		}catch(Exception e) {
			throw new CryptoAlgorithmGetException();
		}
	}
	public static byte[] doSignBySHA256withRSA(byte[] message, PrivateKey prikey) {
		try {
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initSign(prikey);
			sig.update(message);
			return sig.sign();
		}catch(Exception e) {
			throw new CryptoAlgorithmGetException();
		}
	}
	public static byte[] doSignBySHA256withRSA(List<byte[]> list, PrivateKey prikey) {
		try {
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initSign(prikey);
			for(byte[] bs : list) {
				sig.update(bs);
			}
			return sig.sign();
		}catch(Exception e) {
			throw new CryptoAlgorithmGetException();
		}
	}
	
	public static KeyPair doGenerateRSAKeyPair() {
		KeyPairGenerator gen;
		try {
			gen = KeyPairGenerator.getInstance("RSA", "BC");
			RSAKeyGenParameterSpec para = new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4);
			gen.initialize(para, SecureRandom.getInstance("NativePRNGNonBlocking"));
		}catch(Exception e) {
			throw new CryptoAlgorithmGetException();
		}
		KeyPair pair = gen.generateKeyPair();
		return pair;
	}
	
	public static PGPPublicKey doGetPgpPublicKey(byte[] pubkeyBytes) {
		ByteArrayInputStream bais = new ByteArrayInputStream(pubkeyBytes);
		InputStream  is;
		PGPPublicKey pubkey = null;
		try {
			is = PGPUtil.getDecoderStream(bais);
			PGPPublicKeyRingCollection pgpPub = new BcPGPPublicKeyRingCollection(is);
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
		}catch(Exception e) {
			throw new WrongPgpFileException();
		}
		if(pubkey == null) throw new WrongPgpFileException();	
		return pubkey;
	}

	public static byte[] doGetFingerPrint(byte[] keyFile) {
		return doGetPgpPublicKey(keyFile).getFingerprint();
	}
	
	public static PGPPrivateKey doGetPgpPrivateKey(byte[] keyFile, char[] password) {
		PGPSecretKey seckey = null;
		PGPPrivateKey prikey = null;
		try {
			PGPSecretKeyRingCollection pgpSec = new BcPGPSecretKeyRingCollection(
					PGPUtil.getDecoderStream(new ByteArrayInputStream(keyFile)));
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
			PBESecretKeyDecryptor pkd = new BcPBESecretKeyDecryptorBuilder(
					new BcPGPDigestCalculatorProvider()).build(password);
			prikey = seckey.extractPrivateKey(pkd);	
		}catch(Exception e) {
			throw new WrongPgpFileException();
		}
		return prikey;
	}
	
	public static byte[] doPgpSignAndEncrypt(PGPPublicKey pubkey, 
			PGPPrivateKey prikey, byte[] target, String filename) {
		PGPSignatureGenerator sGen = new PGPSignatureGenerator(
				new BcPGPContentSignerBuilder(pubkey.getAlgorithm(),
						PGPUtil.SHA256));
		try{
			sGen.init(PGPSignature.BINARY_DOCUMENT,prikey);
		}catch(Exception e) {
			throw new WrongPgpFileException();
		}
		Iterator<String> it = pubkey.getUserIDs();
		if(it.hasNext()) {
			PGPSignatureSubpacketGenerator spGen = new PGPSignatureSubpacketGenerator();
			spGen.setSignerUserID(false, it.next());
			sGen.setHashedSubpackets(spGen.generate());
		}
		PGPCompressedDataGenerator cGen = new PGPCompressedDataGenerator(PGPCompressedData.ZLIB);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BCPGOutputStream bOut;
		try {
			bOut = new BCPGOutputStream(cGen.open(out));
			sGen.generateOnePassVersion(false).encode(bOut);
		}catch(Exception e) {
			throw new WrongPgpFileException();
		}
		
		PGPLiteralDataGenerator lGen = new PGPLiteralDataGenerator();
		OutputStream lOut ;
		try{
			lOut= lGen.open(bOut, PGPLiteralData.BINARY,filename, target.length, new Date());
			lOut.write(target);
			lOut.flush();
			sGen.update(target);
			lGen.close();
			sGen.generate().encode(bOut);
			bOut.close();
		}catch(Exception e) {
			throw new WrongPgpFileException();
		}

		byte[] bytes = out.toByteArray();
		PGPDataEncryptorBuilder builder;
		try {
			builder = new BcPGPDataEncryptorBuilder(PGPEncryptedData.AES_256)
				.setWithIntegrityPacket(true).setSecureRandom(SecureRandom.getInstance("NativePRNGNonBlocking"));
		}catch(Exception e) {
			throw new CryptoAlgorithmGetException();
		}
		PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(builder);
		PublicKeyKeyEncryptionMethodGenerator gen = new BcPublicKeyKeyEncryptionMethodGenerator(
				pubkey).setSecureRandom(new SecureRandom());
		cPk.addMethod(gen);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		try {
			ArmoredOutputStream aos = new ArmoredOutputStream(resultStream);
			OutputStream cOut = cPk.open(aos, bytes.length);
			cOut.write(bytes);
			cOut.flush();
			cOut.close();
		}catch(Exception e) {
			throw new WrongPgpFileException();
		}
		return resultStream.toByteArray();
	}
}
