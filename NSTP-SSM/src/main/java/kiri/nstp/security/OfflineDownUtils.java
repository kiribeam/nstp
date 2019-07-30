package kiri.nstp.security;

public class OfflineDownUtils {

	private static byte[] generateM1(int authId, int keyId) {
		byte[] m1 = new byte[16];
		m1[15] = (byte)((keyId << 4) | (authId & 0xf)); 
		return m1;
	}

	private static byte[] generateM2(int count, String keyFlag, byte[] keyBytes,
			byte[] k1) {
		byte[] mem = new byte[32];

		mem[0] = (byte)((count >>> 20) & 0xff);
		mem[1] = (byte)((count >>> 12) & 0xff);
		mem[2] = (byte)((count >>> 4) & 0xff);
		mem[3] = (byte)((count << 4) & 0xff);
		
		byte flag = (byte) Integer.parseInt(keyFlag, 2);
		mem[3] = (byte)(mem[3] | ((byte) ((flag>>>2) & 0xf)));
		mem[4] = (byte)((flag << 6) & 0xff);
		System.arraycopy(keyBytes, 0, mem, 16, 16);
		
		byte[] result = new byte[32];
		System.arraycopy(CryptoUtils.doAESCBC128(mem, k1, new byte[16]),
				0, result, 0, result.length);
		return result;
	}
	private static byte[] generateM3(byte[] m1, byte[] m2, byte[] k2) {
		byte[] mem = new byte[m1.length+m2.length];
		System.arraycopy(m1, 0, mem, 0, m1.length);
		System.arraycopy(m2, 0, mem, m1.length, m2.length);
		byte[] result = CryptoUtils.doCmac(mem, k2);
		return result;
		
	}
	public static byte[] generateM123(int authId, int KeyId, int count,
			String keyFlag, byte[] keyBytes, byte[] authKey, 
			byte[] keyUpdateEncC, byte[] keyUpdateMacC) {
		byte[] m1 = generateM1(authId, KeyId);
		byte[] k1 = CryptoUtils.doKDF(authKey, keyUpdateEncC); 
		byte[] m2 = generateM2(count, keyFlag, keyBytes, k1);
		byte[] k2 = CryptoUtils.doKDF(authKey, keyUpdateMacC);
		byte[] m3 = generateM3(m1, m2, k2);
		byte[] result = new byte[64];
		System.arraycopy(m1, 0, result, 0, m1.length);
		System.arraycopy(m2, 0, result, m1.length, m2.length);
		System.arraycopy(m3, 0, result, m1.length+m2.length, m3.length);
		return result;
	}
	public static byte[] generateM4(int count, byte[] keyBytes,
			byte[] keyUpdateEnc) {
		byte[] m4 = new byte[16];
		byte[] mem = new byte[16];
		mem[0] = (byte)((count >>> 20) & 0xff);
		mem[1] = (byte)((count >>> 12) & 0xff);
		mem[2] = (byte)((count >>> 4) & 0xff);
		mem[3] = (byte)((count << 4) & 0xff);
		mem[3] = (byte)((mem[3] & 0xff) | 1<<3);
		byte[] k3 = CryptoUtils.doKDF(keyBytes, keyUpdateEnc);
		System.arraycopy(CryptoUtils.doAESECB128(mem, k3),0, m4, 0, m4.length);
		return m4;
	}
}
