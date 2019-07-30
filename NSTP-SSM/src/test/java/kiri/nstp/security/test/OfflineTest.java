package kiri.nstp.security.test;

import org.junit.jupiter.api.Test;

import kiri.nstp.security.OfflineDownUtils;
import kiri.utils.HexUtils;

public class OfflineTest {
	@Test
	public void run() {
		byte[] keyBytes = 
				HexUtils.hexStringToByteArray("9D4351A7654C938B7929D14FD6A87AE3");
		byte[] authKey = 
				HexUtils.hexStringToByteArray("00000000000000000000000000000000");
		byte[] keyEnc =
				HexUtils.hexStringToByteArray("010153484500800000000000000000B0");
		byte[] keyMac =
				HexUtils.hexStringToByteArray("010253484500800000000000000000B0");

		byte[] result = OfflineDownUtils.generateM123(4, 4, 0, "000000",
				keyBytes, authKey, keyEnc, keyMac);
		System.out.println(HexUtils.byteArrayToHexString(result));
		System.out.println(HexUtils.byteArrayToHexString(OfflineDownUtils.generateM4(0, keyBytes, keyEnc)));
		keyBytes = 
				HexUtils.hexStringToByteArray("754D7499668C407AA5D71FF2C7121292");
		authKey = 
				HexUtils.hexStringToByteArray("9D4351A7654C938B7929D14FD6A87AE3");
		result = OfflineDownUtils.generateM123(4, 4, 1, "000100",
				keyBytes, authKey, keyEnc, keyMac);
		System.out.println(HexUtils.byteArrayToHexString(result));	
		System.out.println(HexUtils.byteArrayToHexString(OfflineDownUtils.generateM4(1, keyBytes, keyEnc)));
	}

}
