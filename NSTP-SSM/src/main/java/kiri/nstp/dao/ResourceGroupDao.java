package kiri.nstp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kiri.nstp.pojo.ResourceGroup;

public interface ResourceGroupDao {
	List<ResourceGroup> getPermittedGroup(@Param("username") String username, @Param("permission") String permission);
	List<ResourceGroup> list(String group);
	void add(ResourceGroup rg);
	void del(Integer id);
	ResourceGroup getById(Integer id);
	

}
