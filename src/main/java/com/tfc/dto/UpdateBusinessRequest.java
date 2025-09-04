package com.tfc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Update business Model")
public class UpdateBusinessRequest {

	@ApiModelProperty(notes = "Id of the Business model which need to update the records", name = "id", required = true ,value="1")
	private long id;
	@ApiModelProperty(notes = "Email of the Business", name = "email", required = true, value = "test@tfc.com")
	private String email;
	//@ApiModelProperty(notes = "Token of the Business", name = "token", required = true, value = "test-token")
	//private String token;
	@ApiModelProperty(notes = "Name of the Business", name = "name", required = true, value = "Global PVT LTD")
	private String name;
	@ApiModelProperty(notes = "Link of the Business", name = "link", required = true, value = "https://global.com")
	private String link;
	
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
	/*public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}*/
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
