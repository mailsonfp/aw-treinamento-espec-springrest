package com.algaworks.algafood.api.model.output;

import java.math.BigDecimal;

public class RestauranteModelOutput {
	
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaModelOutput cozinha;
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoModelOutput endereco;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public CozinhaModelOutput getCozinha() {
		return cozinha;
	}
	public void setCozinha(CozinhaModelOutput cozinha) {
		this.cozinha = cozinha;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Boolean getAberto() {
		return aberto;
	}
	public void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}
	public EnderecoModelOutput getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoModelOutput endereco) {
		this.endereco = endereco;
	}
}
