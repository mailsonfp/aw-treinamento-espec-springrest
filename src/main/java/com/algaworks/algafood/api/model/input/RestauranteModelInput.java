package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class RestauranteModelInput {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdModelInput cozinha;
	
	@Valid
	@NotNull
	private EnderecoModelInput endereco;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}
	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}
	public CozinhaIdModelInput getCozinha() {
		return cozinha;
	}
	public void setCozinha(CozinhaIdModelInput cozinha) {
		this.cozinha = cozinha;
	}
	public EnderecoModelInput getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoModelInput endereco) {
		this.endereco = endereco;
	}
	
}
