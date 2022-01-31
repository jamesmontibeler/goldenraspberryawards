package com.texoit.goldenraspberryawards.resource.award;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.texoit.goldenraspberryawards.service.producer.ProducerService;


@RestController
@RequestMapping(value = "/awardinterval")
public class AwardIntervalResource {
	
	private int cacheMaxAgeInMinutes = 5;
	
	@Autowired
	protected ProducerService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<AwardInterval> findAll() {
		CacheControl cacheControl = CacheControl.maxAge(cacheMaxAgeInMinutes, TimeUnit.MINUTES);		
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(service.getAwardInterval());
	}
	
}
