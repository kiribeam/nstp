package kiri.nstp.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class EcuKey {
	private Integer id;
	@Pattern(regexp="^[A-F|0-9]{32}$")
	private String ecuid;

	@Min(0)
	@Max(20)
	@NotNull
	private Integer keyId = 0;

	private Integer count = 0;

	@Pattern(regexp="^[0|1]{6}$")
	private String keyFlag="";

	@Pattern(regexp="^[A-F|0-9]{32}$")
	private String keyValue="";

	@NotNull
	@Min(0)
	private Integer status=0;
	
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
	public Integer getCount() {
		return count;
	}
	public Integer getKeyId() {
		return keyId;
	}
	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getKeyFlag() {
		return keyFlag;
	}
	public void setKeyFlag(String keyFlag) {
		this.keyFlag = keyFlag;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}

