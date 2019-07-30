package kiri.nstp.security.test;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.junit.jupiter.api.Test;

import kiri.utils.HexUtils;

public class UAESVerifyTest {
	@Test
	public void test() throws Exception{
		FileInputStream fis2 = new FileInputStream("/root/iroiro/test/RefreshCus");
		byte[] file = fis2.readAllBytes();
		fis2.close();
		

		byte[] signature = new byte[256];
		System.arraycopy(file, 0x264, signature, 0, signature.length);
		System.out.println("signature:" + HexUtils.byteArrayToHexString(signature));
		
		byte[] modulus = new byte[257];
		System.arraycopy(file, 100, modulus, 1, 256);
		//reverse(modulus);
		System.out.println("modulus:" + HexUtils.byteArrayToHexString(modulus));
		
		BigInteger m = new BigInteger(modulus);
		System.out.println("m:" + m);
		KeyFactory fac = KeyFactory.getInstance("RSA", "BC");
		RSAPublicKeySpec spec = new RSAPublicKeySpec(m, new BigInteger(65537+""));
		System.out.println("e: "  + new BigInteger(65537+""));
		RSAPublicKey pubkey = (RSAPublicKey) fac.generatePublic(spec);
		System.out.println("In pubkey m:"+HexUtils.byteArrayToHexString(pubkey.getModulus().toByteArray()));
		
		
		Signature sig = Signature.getInstance("SHA256withRSA", "BC");
		sig.initVerify(pubkey);
		byte[] message = new byte[0x262];
		System.arraycopy(file, 2, message, 0, message.length);
		System.out.println("message:" + HexUtils.byteArrayToHexString(message));
		sig.update(message);
		System.out.println("Sig:" + sig.verify(signature));
		
		Cipher cip = Cipher.getInstance("RSA", "BC");
		cip.init(Cipher.DECRYPT_MODE,pubkey);
		byte[] afterDecrypt = cip.doFinal(signature);
		System.out.println("AfterD:" + HexUtils.byteArrayToHexString(afterDecrypt));
		
		MessageDigest md = MessageDigest.getInstance("SHA256", "BC");
		md.update(message);
		System.out.println("SHA256:"+HexUtils.byteArrayToHexString(md.digest()));

		
		/*
		FileInputStream fis1 = new FileInputStream("/root/iroiro/test/base_pubkey.der");
		byte[] rootpk = fis1.readAllBytes();
		fis1.close();
		KeySpec spec2 = new X509EncodedKeySpec(rootpk);
		RSAPublicKey pk = (RSAPublicKey) fac.generatePublic(spec2);
		System.out.println("RootHex:" + HexUtils.byteArrayToHexString(
				pk.getModulus().toByteArray()));
		System.out.println("rootE:"+pk.getPublicExponent());
		System.out.println("length:" + pk.getModulus().toByteArray().length);
		System.out.println("RootBI:" + pk.getModulus());
		*/
		
		
	}
	public void reverse(byte[] b) {
		int length = b.length;
		int head = 0;
		int tail = length-1;
		while(tail>head) {
			byte tmp = b[head];
			b[head] = b[tail];
			b[tail] = tmp;
			head++;
			tail--;
		}
	}

}
