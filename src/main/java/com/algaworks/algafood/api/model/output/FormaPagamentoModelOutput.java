package com.algaworks.algafood.api.model.output;

import io.swagger.annotations.ApiModelProperty;

public class FormaPagamentoModelOutput {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Cartão de Crédito")
    private String descricao;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
