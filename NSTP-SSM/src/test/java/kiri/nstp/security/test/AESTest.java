package kiri.nstp.security.test;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;

import kiri.nstp.security.CryptoUtils;
import kiri.utils.HexUtils;

public class AESTest {
	
	@Test
	public void run() throws Exception{
		byte[] key = new byte[16];
		byte[] out0 = new byte[32];
		//Arrays.fill(out0, (byte)0x10);
		Cipher cp = Cipher.getInstance("AES/ECB/PKCS7Padding");
		System.out.println(cp.getProvider().toString());
		Key sk = new SecretKeySpec(key, "AES");
		cp.init(Cipher.ENCRYPT_MODE, sk);
		System.out.println(HexUtils.byteArrayToHexString(cp.update(out0)));
		byte[] result2= cp.doFinal();
		System.out.println(HexUtils.byteArrayToHexString(result2));
		System.out.println("~~~~~~~~~~~~~~");
		System.out.println(HexUtils.byteArrayToHexString(CryptoUtils.doAESECB128(out0, key)));
		System.out.println("CBCBDCBCBCBCBCBCB");
		System.out.println(HexUtils.byteArrayToHexString(CryptoUtils.doAESCBC128(out0, key, key)));
		System.out.println(HexUtils.byteArrayToHexString(out0));	
	}

}
