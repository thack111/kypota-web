package com.hacksnet.kypota.model;

public class ContestYear {
	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public boolean isSelected(String year) {
		if (this.year.equals(year)) {
			return true;
		} else {
			return false;
		}
	}
}
