package kiri.nstp.pojo;

import javax.validation.constraints.Pattern;

public class EcuInfo {
	private Integer id;
	@Pattern(regexp="^[A-F|0-9]{32}$")
	private String ecuid;
	private String altertime;
	@Pattern(regexp="^[a-z|0-9|A-Z]{0,20}$")
	private String username;
	private String macAddress;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEcuid() {
		return ecuid;
	}
	public void setEcuid(String ecuid) {
		this.ecuid = ecuid;
	}

	public String getAltertime() {
		return altertime;
	}
	public void setAltertime(String altertime) {
		this.altertime = altertime;
	}
	public String getUsername() {
		return username;
	}
	public void setUser(String username) {
		this.username = username;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
}
