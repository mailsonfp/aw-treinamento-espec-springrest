package com.algaworks.algafood.domain.exception;

import org.springframework.validation.BindingResult;

public class ValidacaoPatchException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private BindingResult bindingResult;

	public ValidacaoPatchException(BindingResult bindingResult) {
		super();
		this.bindingResult = bindingResult;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}
	
}
