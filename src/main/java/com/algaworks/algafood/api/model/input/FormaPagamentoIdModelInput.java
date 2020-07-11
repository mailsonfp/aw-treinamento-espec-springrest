package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

public class FormaPagamentoIdModelInput {
	@NotNull
    private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
