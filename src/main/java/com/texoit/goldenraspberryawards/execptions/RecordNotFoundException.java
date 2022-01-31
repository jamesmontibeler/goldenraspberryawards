package com.texoit.goldenraspberryawards.execptions;

public class RecordNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 8490653332039483070L;

	public RecordNotFoundException(String mensagem) {
		super(mensagem);
	}
	
	public RecordNotFoundException(Object id) {
		
		super(String.format("Record %s not found.", id));
	}
	
	public RecordNotFoundException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
