package com.texoit.goldenraspberryawards.service.producer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.texoit.goldenraspberryawards.domain.producer.Producer;
import com.texoit.goldenraspberryawards.domain.producer.ProducerRepository;
import com.texoit.goldenraspberryawards.resource.award.AwardInterval;
import com.texoit.goldenraspberryawards.resource.award.AwardIntervalDetail;
import com.texoit.goldenraspberryawards.service.BaseCrudService;


@Service
public class ProducerService extends BaseCrudService<UUID, Producer, ProducerRepository> {	
	
	public Iterable<Producer> findByNameIgnoreCase(String name) {
		return repository.findByNameIgnoreCase(name);
	}
	
	public Set<AwardIntervalDetail> findProducersWithShortestIntervalBetweenAwards() {
		List<AwardIntervalDetail> awardIntervalSet = new ArrayList<>();		
		repository.findProducersWithMoreThanOneAward().forEach(p -> {
			AwardIntervalDetail aid = new AwardIntervalDetail();
			aid.setProducer(p.getName());		
			List<Integer> years = repository.findYearOfProducerMovies(p.getId().toString());
			aid.setPreviousWin(years.get(0));
			aid.setFollowingWin(years.get(1));
			aid.setInterval(aid.getFollowingWin() - aid.getPreviousWin());
			for (int i = 2; i < years.size(); i++) {
				Integer interval = (years.get(i) - years.get(i-1));
				if (interval < aid.getInterval()) {
					aid.setPreviousWin(years.get(i-1));
					aid.setFollowingWin(years.get(i));
					aid.setInterval(interval);
				}
			}			
			awardIntervalSet.add(aid);
		});
		Integer shortestInterval = Collections.min(awardIntervalSet.stream().map(AwardIntervalDetail::getInterval).collect(Collectors.toList()));
		return awardIntervalSet.stream().filter(aid -> aid.getInterval() == shortestInterval).collect(Collectors.toSet());
	}
	
	public Set<AwardIntervalDetail> findProducersWithLongestIntervalBetweenAwards() {
		Set<AwardIntervalDetail> awardIntervalSet = new HashSet<>();		
		repository.findProducersWithMoreThanOneAward().forEach(p -> {
			AwardIntervalDetail aid = new AwardIntervalDetail();
			aid.setProducer(p.getName());
			List<Integer> years = repository.findYearOfProducerMovies(p.getId().toString());
			aid.setPreviousWin(years.get(0));
			aid.setFollowingWin(years.get(1));
			aid.setInterval(aid.getFollowingWin() - aid.getPreviousWin());
			for (int i = 2; i < years.size(); i++) {
				Integer interval = (years.get(i) - years.get(i-1));
				if (interval > aid.getInterval()) {
					aid.setPreviousWin(years.get(i-1));
					aid.setFollowingWin(years.get(i));
					aid.setInterval(interval);
				}
			}		
			awardIntervalSet.add(aid);
		});
		Integer longestInterval = Collections.max(awardIntervalSet.stream().map(AwardIntervalDetail::getInterval).collect(Collectors.toList()));
		return awardIntervalSet.stream().filter(aid -> aid.getInterval() == longestInterval).collect(Collectors.toSet());		
	}
	
	
	public AwardInterval getAwardInterval() {		
		return new AwardInterval()
				.setMin(findProducersWithShortestIntervalBetweenAwards()) 
				.setMax(findProducersWithLongestIntervalBetweenAwards());
	}
}
