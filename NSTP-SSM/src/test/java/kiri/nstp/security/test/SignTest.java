package kiri.nstp.security.test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import kiri.nstp.security.CryptoUtils;
import kiri.utils.HexUtils;

public class SignTest {
	
	@Test
	public void run() throws Exception{

		FileInputStream fis1 = new FileInputStream("/root/iroiro/test/base_userkey.der");
		FileChannel keyFile = fis1.getChannel();
		FileInputStream fis2 = new FileInputStream("/root/iroiro/test/RefreshCus");
		FileChannel dataFile = fis2.getChannel();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		ByteBuffer buffer = ByteBuffer.allocate(4096);
		int tmp;
		while((tmp = keyFile.read(buffer)) != -1) {
			buffer.flip();
			while(buffer.hasRemaining())
				bos.write(buffer.get());
			buffer.clear();
		}
		byte[] keyByte = bos.toByteArray();
		//System.out.println("keyb:"+ HexUtils.byteArrayToHexString(keyByte));

		byte[] data = new byte[0x164];
		tmp=0;
		tmp = dataFile.read(buffer);
		System.out.println("tmp:"+tmp);
		System.out.println("Remain:"+buffer.remaining());
		buffer.flip();
		buffer.get(data);
		System.out.println("DATA:"+ HexUtils.byteArrayToHexString(data));

		PKCS8EncodedKeySpec priSpec = new PKCS8EncodedKeySpec(keyByte);
		KeyFactory keyFac = KeyFactory.getInstance("RSA");
		PrivateKey priKey = keyFac.generatePrivate(priSpec);
		Signature sig = Signature.getInstance("SHA256withRSA");
		sig.initSign(priKey);
		sig.update(data, 0x4a, 0x154-0x4a);
		sig.update(data,0x154, 0x10);
		byte[] result = sig.sign();
		System.out.println("~~~~~~~~\nResult:" + HexUtils.byteArrayToHexString(result));
		
		byte[] message = new byte[0x164-0x4a];
		System.arraycopy(data, 0x4a, message, 0, message.length);
		System.out.println("Cry:" + HexUtils.byteArrayToHexString(
				CryptoUtils.doSignBySHA256withRSA(message, priKey)));
		
		byte[] message2 = new byte[0x154-0x4a];
		byte[] message3 = new byte[0x10];
		System.arraycopy(data, 0x4a, message2, 0, message2.length);
		System.arraycopy(data, 0x154, message3, 0, message3.length);
		LinkedList<byte[]> list = new LinkedList<>();
		list.add(message2);
		list.add(message3);
		System.out.println("list:" + HexUtils.byteArrayToHexString(
				CryptoUtils.doSignBySHA256withRSA(list, priKey)));
		
		fis1.close();
		fis2.close();
		keyFile.close();
		dataFile.close();

		FileInputStream fis3 = new FileInputStream("/root/iroiro/test/base_pubkey.der");
		byte[] rootpk = fis3.readAllBytes();
		fis3.close();
		KeySpec spec2 = new X509EncodedKeySpec(rootpk);
		RSAPublicKey pk = (RSAPublicKey) keyFac.generatePublic(spec2);
		sig.initVerify(pk);
		sig.update(data, 0x4a, 0x164-0x4a);
		System.out.println("verify:"+sig.verify(result));
		
	}

}
