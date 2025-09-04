package com.tfc.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String link;
	
	@Column(name="cost_unit")
	private String costUnit;
	
	private String location;
	
	@Column(name="updated_at")
	private Date updatedAt;
	
	@Column(name="created_at")
	private Date createdAt;
	
	@ManyToOne
	@JoinColumn(name = "ngo_id", nullable = false)
	private Ngo ngo;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
    private List<Donation> donationList; 

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getCostUnit() {
		return costUnit;
	}

	public void setCostUnit(String contUnit) {
		this.costUnit = contUnit;
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

	public Ngo getNgo() {
		return ngo;
	}

	public void setNgo(Ngo ngo) {
		this.ngo = ngo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Donation> getDonationList() {
		return donationList;
	}

	public void setDonationList(List<Donation> donationList) {
		this.donationList = donationList;
	}
	
}
