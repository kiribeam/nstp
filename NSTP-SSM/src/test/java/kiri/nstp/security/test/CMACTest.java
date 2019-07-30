package kiri.nstp.security.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kiri.nstp.security.CryptoUtils;
import kiri.utils.HexUtils;

public class CMACTest {
	@Test
	public void testrun() throws Exception{
		String hexKey = "25 1A 052A E7 EB 9E 05 A5 40 3F FE 80 E8 F1 94";
		String challenge = " 01 E9 B3 28 9A 06 64 83 E2 73 14 56 4B F2 44 EF AC" +
				"F5 57 43 CD 75 8F DC 08 B2 0F 09 70 B1 AD 0F";
		hexKey = hexKey.replaceAll(" ", "");
		challenge = challenge.replaceAll(" ", "");
		String result = CryptoUtils.doCmac(challenge, hexKey);
		System.out.println(result);
		FileOutputStream os = new FileOutputStream("/root/iroiro/cha2");
		os.write(HexUtils.hexStringToByteArray(challenge));
		os.close();
		FileInputStream is = new FileInputStream("/root/iroiro/ch3");
		System.out.println(HexUtils.byteArrayToHexString(is.readAllBytes()));
		is.close();
		Assertions.assertNotNull(result);
	}

}
