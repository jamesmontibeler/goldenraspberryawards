package com.texoit.goldenraspberryawards.domain;

public interface BaseEntity<ID> {
	
	public ID getId();
	
	public void setId(ID id);

}
