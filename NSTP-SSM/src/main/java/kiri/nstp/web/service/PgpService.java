package kiri.nstp.web.service;

import java.util.List;

import kiri.nstp.dto.PgpSearchMessage;
import kiri.nstp.pojo.UserPgp;

public interface PgpService {
	
	List<UserPgp> getPgpRecord(PgpSearchMessage psm);
	
	void pgpUploadPublicKey(PgpSearchMessage psm, byte[] file);
	
	void pgpDropPublicKey(PgpSearchMessage psm);
	
	void pgpTrust(PgpSearchMessage psm, List<Integer> list);
	
	void pgpUntrust(PgpSearchMessage psm, List<Integer> list);
	
	void pgpDelete(PgpSearchMessage psm, List<Integer> list);

}
