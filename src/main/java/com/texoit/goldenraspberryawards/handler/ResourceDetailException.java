package com.texoit.goldenraspberryawards.handler;

import java.util.Map;

public class ResourceDetailException {
	
	private Long status;
	private String message;
	private Map<String, String> details;
	private Long timestap;
	
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	public Long getTimestap() {
		return timestap;
	}
	public void setTimestap(Long timestap) {
		this.timestap = timestap;
	}
	public Map<String, String> getDetails() {
		return details;
	}
	public void setDetails(Map<String, String> details) {
		this.details = details;
	}	
	
}
