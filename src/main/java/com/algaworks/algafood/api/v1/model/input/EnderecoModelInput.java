package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class EnderecoModelInput {
	
	@ApiModelProperty(example = "00000-000", required = true)
	@NotBlank
	private String cep;
	
	@ApiModelProperty(example = "Avenida Paulista", required = true)
	@NotBlank
	private String logradouro;
	
	@ApiModelProperty(example = "0000", required = true)
	@NotBlank
	private String numero;
	
	@ApiModelProperty(example = "Apartamento 000")
	private String complemento;
	

	@ApiModelProperty(example = "Nome do Bairro", required = true)
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeIdModelInput cidade;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public CidadeIdModelInput getCidade() {
		return cidade;
	}

	public void setCidade(CidadeIdModelInput cidade) {
		this.cidade = cidade;
	}
}
