package kiri.nstp.web.service;

import java.util.List;
import java.util.Set;

import kiri.nstp.pojo.ResourceGroup;

public interface ResourceGroupService {
	Set<ResourceGroup> getPermittedGroup(String username, String permission);
	List<ResourceGroup> list(String group, Integer page);
	void add(ResourceGroup rg);
	void del(Integer id);

}
