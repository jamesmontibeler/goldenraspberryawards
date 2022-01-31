package com.texoit.goldenraspberryawards.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.ReflectionUtils;

import com.texoit.goldenraspberryawards.domain.BaseEntity;
import com.texoit.goldenraspberryawards.domain.BaseRepository;
import com.texoit.goldenraspberryawards.execptions.RecordNotFoundException;


public abstract class BaseCrudService<ID, T extends BaseEntity<ID>, B extends BaseRepository<T, ID>>  {
	
	@Autowired
	protected B repository;
	
	public List<T> findAll() {
		return repository.findAll();
	}
	
	public Optional<T> findById(ID id) {		
		return repository.findById(id);
	}
	
	public T findByIdWithException(ID id) {		
		return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
	}
	
	public void checkRecordExistence(T obj) {
		findByIdWithException(obj.getId());
	}
	
	public T save(T obj) {
		obj.setId(null);
		return repository.save(obj);
	}
	
	public T update(T obj) {
		this.checkRecordExistence(obj);
		return repository.save(obj);
	}
	
	public T patch(ID id, Map<Object, Object> fields) {
		T obj = this.findByIdWithException(id);
		fields.forEach((key, value) -> {
			Field field  = ReflectionUtils.findField(obj.getClass(), (String) key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, obj, value);
		});	
		return repository.save(obj);
	}
	
	public void deleteById(ID id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException(id);
		}
	}

}
