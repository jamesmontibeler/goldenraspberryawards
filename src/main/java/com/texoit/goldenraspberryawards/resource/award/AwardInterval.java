package com.texoit.goldenraspberryawards.resource.award;

import java.util.Set;

public class AwardInterval {
	
	private Set<AwardIntervalDetail> min;
	private Set<AwardIntervalDetail> max;
	
	public Set<AwardIntervalDetail> getMin() {
		return min;
	}
	
	public AwardInterval setMin(Set<AwardIntervalDetail> min) {
		this.min = min;
		return this;
	}
	
	public Set<AwardIntervalDetail> getMax() {
		return max;
	}
	
	public AwardInterval setMax(Set<AwardIntervalDetail> max) {
		this.max = max;
		return this;
	}
	
	
	
}
