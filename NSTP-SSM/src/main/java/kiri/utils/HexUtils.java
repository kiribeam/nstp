package kiri.utils;

import java.util.List;

public class HexUtils {
	
	public static String byteArrayToHexString(byte[] array) {
		char[] axis = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder();
		for(byte b : array) {
			sb.append(axis[(b & 0xf0)>>>4]);
			sb.append(axis[b & 0xf]);
		}
		return sb.toString();
	}

	public static byte[] hexStringToByteArray(String hexString) {
		String err = "Input hex string wrong!" + "/n" + hexString;
		String str = "0123456789ABCDEF";
		int length = hexString.length();
		char[] chars = hexString.toUpperCase().toCharArray();
		if(length %2 != 0) 
			throw new IllegalArgumentException(err);
		byte[] result = new byte[length/2];
		for(int i=0; i<length/2; i++) {
			int l = str.indexOf(chars[2*i]);
			int r = str.indexOf(chars[2*i+1]);
			if(l<0 || r<0) throw new IllegalArgumentException(err);
			l = l*16 + r;
			result[i] = (byte) (l & 0xff);
		}
		return result;
	}

	
	public static byte[] mergeByteArrays(List<byte[]> list) {
		int size = 0;
		for(byte[] b: list) {
			size += b.length;
		}
		byte[] result = new byte[size];
		int pos = 0;
		for(byte[] b : list) {
			System.arraycopy(b, 0, result, pos, b.length);
			pos+=b.length;
		}
		return result;
	}

}
