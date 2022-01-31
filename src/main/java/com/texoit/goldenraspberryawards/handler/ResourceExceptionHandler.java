package com.texoit.goldenraspberryawards.handler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.texoit.goldenraspberryawards.execptions.RecordNotFoundException;

@ControllerAdvice
class ResourceExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ResourceDetailException> handlerRecordNotFound(RecordNotFoundException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		ResourceDetailException detail = new ResourceDetailException();
		detail.setStatus(Long.valueOf(status.value()));
		detail.setMessage(e.getMessage());
		detail.setTimestap(System.currentTimeMillis());

		return ResponseEntity.status(status).body(detail);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<ResourceDetailException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Map<String, String> errors = new HashMap<>();
	    e.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });

		ResourceDetailException detail = new ResourceDetailException();
		detail.setStatus(Long.valueOf(status.value()));
		detail.setMessage("Not valid due to validation errors! ");
		detail.setDetails(errors);
		detail.setTimestap(System.currentTimeMillis());
		
		return ResponseEntity.status(status).body(detail);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResourceDetailException> handlerRecordNotFound(IllegalArgumentException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		ResourceDetailException detail = new ResourceDetailException();
		detail.setStatus(Long.valueOf(status.value()));
		detail.setMessage(e.getMessage());
		detail.setTimestap(System.currentTimeMillis());

		return ResponseEntity.status(status).body(detail);
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ResourceDetailException> handlerRecordNotFound(SQLIntegrityConstraintViolationException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		ResourceDetailException detail = new ResourceDetailException();
		detail.setStatus(Long.valueOf(status.value()));
		detail.setMessage(e.getMessage());
		detail.setTimestap(System.currentTimeMillis());

		return ResponseEntity.status(status).body(detail);
	}
	
	

}
