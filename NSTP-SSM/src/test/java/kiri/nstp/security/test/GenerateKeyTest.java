package kiri.nstp.security.test;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

import org.junit.jupiter.api.Test;

import kiri.nstp.security.CryptoUtils;
import kiri.utils.HexUtils;

public class GenerateKeyTest {
	@Test
	public void gen()  throws Exception{
		KeyPair pair = CryptoUtils.doGenerateRSAKeyPair();
		RSAPublicKey pub = (RSAPublicKey)pair.getPublic();
		System.out.println("E:"+pub.getPublicExponent());
		System.out.println("keyLen:"+pub.getModulus().toString(2).length());
		
		byte[] encode = pub.getEncoded();
		System.out.println("HexEncode:"+ HexUtils.byteArrayToHexString(encode));
		
		BigInteger bi = pub.getModulus();
		byte[] bis = bi.toByteArray();
		System.out.println("BigIntegerByte:" + HexUtils.byteArrayToHexString(bis));
		
		RSAPublicKeySpec spec = new RSAPublicKeySpec(new BigInteger(bis), new BigInteger(65537+""));
		KeyFactory fac = KeyFactory.getInstance("RSA");
		RSAPublicKey pubRe = (RSAPublicKey) fac.generatePublic(spec);
		System.out.println("Equals:"+pubRe.getModulus().equals(pub.getModulus()));

		
	}

}
