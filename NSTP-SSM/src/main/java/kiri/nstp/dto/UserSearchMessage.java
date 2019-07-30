package kiri.nstp.dto;

import java.util.Set;

import javax.validation.constraints.Pattern;

public class UserSearchMessage implements ResourceSearchMessage{
	private Integer id;
	@Pattern(regexp="^[A-Z|a-z|0-9]{0,20}$")
	private String username;
	private String password;
	private String email;
	private String phone;
	private Integer status;
	private String userGroup;
	private Set<String> userGroupSet;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String getUserGroup() {
		return userGroup;
	}
	@Override
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	@Override
	public Set<String> getUserGroupSet() {
		return userGroupSet;
	}
	@Override
	public void setUserGroupSet(Set<String> userGroupSet) {
		this.userGroupSet = userGroupSet;
	}

}
