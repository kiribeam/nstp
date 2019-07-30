package kiri.nstp.dto;

import javax.validation.constraints.Pattern;

public class UserSuite {
	@Pattern(regexp="^[A-Z|0-9|a-z|@]{4,20}$")
	private String username;
	@Pattern(regexp="^.{6,25}$")
	private String password;
	@Pattern(regexp="^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$")
	private String mail;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
}
