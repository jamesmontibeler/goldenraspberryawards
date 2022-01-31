package com.texoit.goldenraspberryawards.resource.producer;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.texoit.goldenraspberryawards.domain.producer.Producer;
import com.texoit.goldenraspberryawards.resource.BaseCrudResource;
import com.texoit.goldenraspberryawards.service.producer.ProducerService;


@RestController
@RequestMapping(value = "/producers")
public class ProducerResource extends BaseCrudResource<UUID, Producer, ProducerService> {
	
	public ProducerResource() {		
		setFindByIdCacheMaxAgeInMinutes(5);
	}
	
}
