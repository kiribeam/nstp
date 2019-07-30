package kiri.nstp.security.test;

import java.io.FileInputStream;
import java.security.MessageDigest;

import org.junit.jupiter.api.Test;

import kiri.utils.HexUtils;

public class SHA256Test {
	
	@Test
	public void go() throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA256");
		System.out.println("alg:"+md.getAlgorithm());
		String s = "123456";
		byte[] result1 = md.digest(s.getBytes());
		System.out.println("s:" + HexUtils.byteArrayToHexString(s.getBytes()));
		System.out.println("MD:" + HexUtils.byteArrayToHexString(result1));
		md.reset();
		s = s+"7";
		System.out.println("MD:"+ HexUtils.byteArrayToHexString(md.digest(s.getBytes())));
		md.reset();
		byte[] temp = new byte[result1.length+1];
		temp[temp.length-1] = (byte) 0x37;
		System.arraycopy(result1, 0, temp, 0, result1.length);
		byte[] result2 = md.digest(temp);
		System.out.println("MD:"+ HexUtils.byteArrayToHexString(result2));

	}
	@Test
	public void go2() throws Exception{
		FileInputStream fis2 = new FileInputStream("/root/iroiro/test/AppFile.bin");	
		byte[] file = fis2.readAllBytes();
		fis2.close();
		MessageDigest md = MessageDigest.getInstance("SHA256");
		byte[] plain = new byte[file.length - 0x364];
		System.arraycopy(file, 0x364, plain, 0, file.length-0x364);
		byte result[] = md.digest(plain);
		System.out.println("Hex:" + HexUtils.byteArrayToHexString(result));
	}

}
