package com.tfc.dto;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Component
@ApiModel(description = "Add business Model")
public class AddBusinessRequest {

	@ApiModelProperty(notes = "Email of the Business", name = "email", required = true, value = "test@tfc.com")
	private String email;
	//@ApiModelProperty(notes = "Token of the Business", name = "token", required = true, value = "test-token")
	//private String token;
	@ApiModelProperty(notes = "Name of the Business", name = "name", required = true, value = "Global PVT LTD")
	private String name;
	@ApiModelProperty(notes = "Link of the Business", name = "link", required = true, value = "https://global.com")
	private String link;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
}
