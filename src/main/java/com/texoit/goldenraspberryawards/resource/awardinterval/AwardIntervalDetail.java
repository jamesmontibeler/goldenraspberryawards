package com.texoit.goldenraspberryawards.resource.awardinterval;

public class AwardIntervalDetail {

	private String producer;
	private Integer interval;
	private Integer previousWin;
	private Integer followingWin;
	
	public AwardIntervalDetail() {
		interval = 0;
		previousWin = 0;
		followingWin = 0;
	}
	
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public Integer getPreviousWin() {
		return previousWin;
	}
	public void setPreviousWin(Integer previousWin) {
		this.previousWin = previousWin;
	}
	public Integer getFollowingWin() {
		return followingWin;
	}
	public void setFollowingWin(Integer followingWin) {
		this.followingWin = followingWin;
	}
	
	
}
