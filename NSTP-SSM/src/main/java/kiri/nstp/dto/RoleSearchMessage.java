package kiri.nstp.dto;

import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RoleSearchMessage implements ResourceSearchMessage{
	private Integer id;
	@Pattern(regexp="^[0-9|a-z|A-Z]{0,20}$")
	private String username;
	@Pattern(regexp="^[0-9|a-z|A-Z]{0,20}$")
	private String role;
	@Pattern(regexp="^[0-9|a-z|A-Z]{0,32}$")
	private String userGroup;
	private Set<String> userGroupSet;
	@NotNull
	@Min(1)
	private Integer pageNumber = 1;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	public Set<String> getUserGroupSet() {
		return userGroupSet;
	}
	public void setUserGroupSet(Set<String> userGroupSet) {
		this.userGroupSet = userGroupSet;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
}