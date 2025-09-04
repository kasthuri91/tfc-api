package com.tfc.dto;

import java.util.Date;
import java.util.List;

import com.tfc.model.Donation;

public class BusinessResponse {

	private long id;
	private String email;
	private String token;
	private String name;
	private String link;
	private Date updatedAt;
	private Date createdAt;
	
	private List<DonationBusinsessResponse> donationList;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public List<DonationBusinsessResponse> getDonationList() {
		return donationList;
	}
	public void setDonationList(List<DonationBusinsessResponse> donationList) {
		this.donationList = donationList;
	}
	
	
}
