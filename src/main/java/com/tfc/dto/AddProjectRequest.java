package com.tfc.dto;

import com.tfc.model.Ngo;

public class AddProjectRequest {

	private String name;
	private String costUnit;
	private String location;
	private String link;
	private long ngoId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCostUnit() {
		return costUnit;
	}
	public void setCostUnit(String costUnit) {
		this.costUnit = costUnit;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public long getNgoId() {
		return ngoId;
	}
	public void setNgoId(long ngoId) {
		this.ngoId = ngoId;
	}
	
	
}
