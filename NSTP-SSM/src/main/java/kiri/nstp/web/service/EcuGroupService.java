package kiri.nstp.web.service;

import java.util.List;

import kiri.nstp.dto.EcuGroupBlock;
import kiri.nstp.dto.EcuSearchMessage;

public interface EcuGroupService {
	
	List<EcuGroupBlock> list(EcuSearchMessage esm);
	void add(EcuSearchMessage esm);
	void del(EcuSearchMessage esm);

}
