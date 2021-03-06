package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class FormaPagamentoModelInput {
	
	@ApiModelProperty(example = "Cartão de crédito", required = true)
	@NotBlank
    private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
