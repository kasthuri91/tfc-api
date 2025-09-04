package com.tfc.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Error Response Model")
public class CustomErrorResponse {

	@ApiModelProperty(notes = "Current timestamp", name = "timestamp", value = "2020-10-30T18:17:06.000+00:00")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;
	
	@ApiModelProperty(notes = "Error Code", name = "code", value = "200")
	private int code;
	
	@ApiModelProperty(notes = "Error message", name = "error", value = "Data not found")
	private String error;
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
