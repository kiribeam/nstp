package kiri.nstp.dto;

import java.util.Set;

public interface ResourceSearchMessage {

	String getUserGroup();
	void setUserGroup(String userGroup);
	
	Set<String> getUserGroupSet();
	void setUserGroupSet(Set<String> userGroupSet);
	
	String getUsername();
	void setUsername(String username);
	

}
