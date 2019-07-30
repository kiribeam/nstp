package kiri.nstp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserGroupRoleBlock {
	private Integer id;
	//Here must input a user name
	@Pattern(regexp="^[0-9|a-z|A-Z]{4,20}$")
	private String username;
	@NotNull
	@Min(1)
	private Integer rid;
	private String rname;
	private Integer gid;
	@Pattern(regexp="^[0-9|a-z|A-Z]{0,32}$")
	private String gname;
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
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
}
