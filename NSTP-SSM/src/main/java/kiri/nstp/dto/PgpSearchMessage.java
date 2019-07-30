package kiri.nstp.dto;

import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PgpSearchMessage implements ResourceSearchMessage{
	private Integer id;
	@Pattern(regexp="^[0-9|a-f|A-F]{0,32}$")
	private String fingerPrint;
	@Pattern(regexp="^[0-9|a-z|A-Z]{0,20}$")
	private String username;
	@Min(1)
	@NotNull
	private Integer pageNumber=1;
	private Integer status=0;
	@Pattern(regexp="^[0-9|a-z|A-Z]{0,32}$")
	private String userGroup;
	private Set<String> userGroupSet;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFingerPrint() {
		return fingerPrint;
	}
	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
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
