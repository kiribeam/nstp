package kiri.nstp.web.service;

import java.util.List;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.pojo.EcuInfo;

public interface EcuService {
	List<EcuInfo> getEcuList(EcuSearchMessage esm);
	void createEcu(Integer number);
	void createCustomEcu(String ecuid);
	void delEcu(List<Integer> list, EcuSearchMessage esm);
}
