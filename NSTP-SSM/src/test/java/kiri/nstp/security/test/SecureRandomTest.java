package kiri.nstp.security.test;

import java.security.SecureRandom;

import org.junit.jupiter.api.Test;

import kiri.utils.HexUtils;

//Here changes tomcat! in catalina.sh add urandom;
public class SecureRandomTest {
	@Test
	public void run() throws Exception{
		byte[] bytes = new byte[16];
		SecureRandom random = SecureRandom.getInstance("NativePRNGNonBlocking");
		random.nextBytes(bytes);
		System.out.println(HexUtils.byteArrayToHexString(bytes));
	}

}
