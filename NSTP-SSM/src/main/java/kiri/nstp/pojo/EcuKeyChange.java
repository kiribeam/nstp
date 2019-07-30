package kiri.nstp.pojo;

public class EcuKeyChange {
	private Integer id;
	private String ecuid;
	private Integer keyId;
	private String operator;
	private String opValue;
	private String m4;
	private Integer count;
	private String keyFlag;
	public String getKeyFlag() {
		return keyFlag;
	}
	public void setKeyFlag(String keyFlag) {
		this.keyFlag = keyFlag;
	}
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
	public Integer getKeyId() {
		return keyId;
	}
	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}
	public String getOpValue() {
		return opValue;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public void setOpValue(String opValue) {
		this.opValue = opValue;
	}
	public String getM4() {
		return m4;
	}
	public void setM4(String m4) {
		this.m4 = m4;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
