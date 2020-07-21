package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;

public class ItemPedidoModelInput {
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
    private Long produtoId;
    
	@ApiModelProperty(example = "2", required = true)
    @NotNull
    @PositiveOrZero
    private Integer quantidade;
    
	@ApiModelProperty(example = "Tirar o milho e ervilha")
    private String observacao;

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
    
}
