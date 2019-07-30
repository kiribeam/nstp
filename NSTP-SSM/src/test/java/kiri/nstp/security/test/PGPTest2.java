package kiri.nstp.security.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.junit.jupiter.api.Test;

import kiri.nstp.security.CryptoUtils;

public class PGPTest2 {
	@Test
	public void run() throws Exception{
		FileInputStream fis = new FileInputStream("/root/iroiro/pgp/test.asc");
		byte[] pubs = fis.readAllBytes();
		fis.close();
		PGPPublicKey pubkey = CryptoUtils.doGetPgpPublicKey(pubs);
		
		fis = new FileInputStream("/root/iroiro/pgp/server.pri");
		byte[] pris = fis.readAllBytes();
		fis.close();
		PGPPrivateKey prikey = CryptoUtils.doGetPgpPrivateKey(pris, 
				"kiri@nstp".toCharArray());
		
		fis = new FileInputStream("/root/iroiro/pgp/testfile");
		byte[] target = fis.readAllBytes();
		fis.close();
		byte[] result = CryptoUtils.doPgpSignAndEncrypt(pubkey, prikey,
				target, "ok");
		FileOutputStream fos = new FileOutputStream("/root/iroiro/pgp/ok");
		fos.write(result);
		fos.flush();
		fos.close();
		

		
	}

}
