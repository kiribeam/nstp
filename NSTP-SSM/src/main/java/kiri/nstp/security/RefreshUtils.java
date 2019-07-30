package kiri.nstp.security;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import kiri.nstp.exception.CryptoAlgorithmGetException;
import kiri.utils.HexUtils;

public class RefreshUtils {
	
	public static RSAPrivateKey getPrivateKey(String serverPrivateKeyDerFilePath) {
		try(FileInputStream fis = new FileInputStream(serverPrivateKeyDerFilePath)){
			byte[] keyByte = fis.readAllBytes();
			PKCS8EncodedKeySpec priSpec = new PKCS8EncodedKeySpec(keyByte);
			KeyFactory keyFac = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFac.generatePrivate(priSpec);
			return (RSAPrivateKey) priKey;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}	
	}
	
	public static byte[] generateSignerInfo(RSAPrivateKey serverPriKey,
			String signHeader, RSAPublicKey dynamicPubKey) {
		byte[] header = HexUtils.hexStringToByteArray(signHeader);
		byte[] keyBytes = dynamicPubKey.getModulus().toByteArray();
		byte[] signature;
		try {
			Signature sig = Signature.getInstance("SHA256withRSA", "BC");
			sig.initSign(serverPriKey);
			sig.update(header);
			sig.update(keyBytes, keyBytes.length-256, 256);
			signature = sig.sign();
		}catch(Exception e) {
			throw new CryptoAlgorithmGetException();
		}
		byte[] result = new byte[538];
		System.arraycopy(header, 0, result, 0, header.length);
		System.arraycopy(keyBytes, keyBytes.length-256, result, header.length, 256);
		System.arraycopy(signature, 0, result, 282, 256);
		return result;
	}

}
