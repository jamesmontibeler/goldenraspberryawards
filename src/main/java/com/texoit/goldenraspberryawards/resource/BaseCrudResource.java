package com.texoit.goldenraspberryawards.resource;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.texoit.goldenraspberryawards.domain.BaseEntity;
import com.texoit.goldenraspberryawards.service.BaseCrudService;

public abstract class BaseCrudResource<ID, T extends BaseEntity<ID>, B extends BaseCrudService<ID, T, ?>> {
	
	private int findAllCacheMaxAgeInMinutes = 1;
	private int findByIdCacheMaxAgeInMinutes = 1;
	
	@Autowired
	protected B service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<T>> findAll() {
		CacheControl cacheControl = CacheControl.maxAge(findAllCacheMaxAgeInMinutes, TimeUnit.MINUTES);		
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(service.findAll());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody T obj) {		
		obj = service.save(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody T obj, @PathVariable("id") ID id) {
		obj.setId(id);
		obj = service.update(obj);	
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Void> patch(@PathVariable("id") ID id, @RequestBody Map<Object, Object> fields) {		
		service.patch(id, fields);		

		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable("id") ID id) {
		T obj = service.findByIdWithException(id);					
		CacheControl cacheControl = CacheControl.maxAge(findByIdCacheMaxAgeInMinutes, TimeUnit.MINUTES);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") ID id) {
	    service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	protected int getFindAllCacheMaxAgeInMinutes() {
		return findAllCacheMaxAgeInMinutes;
	}

	protected void setFindAllCacheMaxAgeInMinutes(int findAllCacheMaxAgeInMinutes) {
		this.findAllCacheMaxAgeInMinutes = findAllCacheMaxAgeInMinutes;
	}

	protected int getFindByIdCacheMaxAgeInMinutes() {
		return findByIdCacheMaxAgeInMinutes;
	}

	protected void setFindByIdCacheMaxAgeInMinutes(int findByIdCacheMaxAgeInMinutes) {
		this.findByIdCacheMaxAgeInMinutes = findByIdCacheMaxAgeInMinutes;
	}	
	
}
