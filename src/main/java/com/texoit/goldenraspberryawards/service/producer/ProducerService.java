package com.texoit.goldenraspberryawards.service.producer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.texoit.goldenraspberryawards.domain.producer.Producer;
import com.texoit.goldenraspberryawards.domain.producer.ProducerRepository;
import com.texoit.goldenraspberryawards.resource.awardinterval.AwardInterval;
import com.texoit.goldenraspberryawards.resource.awardinterval.AwardIntervalDetail;
import com.texoit.goldenraspberryawards.service.BaseCrudService;


@Service
public class ProducerService extends BaseCrudService<UUID, Producer, ProducerRepository> {	
	
	public Iterable<Producer> findByNameIgnoreCase(String name) {
		return repository.findByNameIgnoreCase(name);
	}
	
	private AwardIntervalDetail getInterval(AwardIntervalDetail aid, List<Integer> years, IntervalType intervalType) {
		if (years.size() < 2) {
			return null;
		}
		aid.setPreviousWin(years.get(0));
		aid.setFollowingWin(years.get(1));
		aid.setInterval(aid.getFollowingWin() - aid.getPreviousWin());
		for (int i = 2; i < years.size(); i++) {
			Integer interval = (years.get(i) - years.get(i-1));
			if ((IntervalType.SHORTEST.equals(intervalType) && interval < aid.getInterval())
					|| (IntervalType.LONGEST.equals(intervalType) && interval > aid.getInterval())){
				aid.setPreviousWin(years.get(i-1));
				aid.setFollowingWin(years.get(i));
				aid.setInterval(interval);
			}
		}	
		return aid;
	}
	
	private Set<AwardIntervalDetail> findProducersWithIntervalBetweenAwards(IntervalType intervalType) {
		List<AwardIntervalDetail> awardIntervalSet = new ArrayList<>();		
		List<Producer> producers = repository.findProducersWithMoreThanOneAward();
		for (Producer producer: producers) {			
			AwardIntervalDetail aid = new AwardIntervalDetail();
			aid.setProducer(producer.getName());		
			List<Integer> years = repository.findYearOfProducerMovies(producer.getId().toString());
			aid = getInterval(aid, years, intervalType);
			if (!ObjectUtils.isEmpty(aid)) { 
				awardIntervalSet.add(aid);
			}
		}
		Function<List<Integer>, Integer> comparator = IntervalType.SHORTEST.equals(intervalType) ? Collections::min : Collections::max;
		Integer shortestInterval = comparator.apply(awardIntervalSet.stream().map(AwardIntervalDetail::getInterval).collect(Collectors.toList()));
		return awardIntervalSet.stream().filter(aid -> aid.getInterval() == shortestInterval).collect(Collectors.toSet());
	}
	
	
	public AwardInterval getAwardInterval() {		
		return new AwardInterval()
				.setMin(findProducersWithIntervalBetweenAwards(IntervalType.SHORTEST)) 
				.setMax(findProducersWithIntervalBetweenAwards(IntervalType.LONGEST));
	}
}
