package com.hacksnet.kypota.model;

public class LogQso {

	private int logId;
	private int qsoId;
	private String freq;
	private String qsoMode;
	private String qsoDate;
	private String sntCall;
	private String sntRst;
	private String sntExch;
	private String rcvCall;
	private String rcvRst;
	private String rcvExch;
	private String transmitterId;
	private String dup;
	private String park2park;
	private String bonus;
	private String band;
	
	
	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public int getQsoId() {
		return qsoId;
	}
	public void setQsoId(int qsoId) {
		this.qsoId = qsoId;
	}
	public String getQsoMode() {
		return qsoMode;
	}
	public void setQsoMode(String qsoMode) {
		this.qsoMode = qsoMode;
	}
	public String getQsoDate() {
		return qsoDate;
	}
	public void setQsoDate(String qsoDate) {
		this.qsoDate = qsoDate;
	}
	public String getSntCall() {
		return sntCall;
	}
	public void setSntCall(String sntCall) {
		this.sntCall = sntCall;
	}
	public String getSntRst() {
		return sntRst;
	}
	public void setSntRst(String sntRst) {
		this.sntRst = sntRst;
	}
	public String getSntExch() {
		return sntExch;
	}
	public void setSntExch(String sntExch) {
		this.sntExch = sntExch;
	}
	public String getRcvCall() {
		return rcvCall;
	}
	public void setRcvCall(String rcvCall) {
		this.rcvCall = rcvCall;
	}
	public String getRcvRst() {
		return rcvRst;
	}
	public void setRcvRst(String rcvRst) {
		this.rcvRst = rcvRst;
	}
	public String getRcvExch() {
		return rcvExch;
	}
	public void setRcvExch(String rcvExch) {
		this.rcvExch = rcvExch;
	}
	public String getTransmitterId() {
		return transmitterId;
	}
	public void setTransmitterId(String transmitterId) {
		this.transmitterId = transmitterId;
	}
	public String getDup() {
		return dup;
	}
	public void setDup(String dup) {
		this.dup = dup;
	}
	public String getPark2park() {
		return park2park;
	}
	public void setPark2park(String park2park) {
		this.park2park = park2park;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getBand() {
		return band;
	}
	public void setBand(String band) {
		this.band = band;
	}
	
	public boolean isValid() {
		if (qsoDate.isEmpty() ||
			!qsoDate.matches("([0-9]){4}-([0-9]){2}-([0-9]){2} ([0-9]){4}")) {
			return false;
		}
		return true;
	}
	
}
