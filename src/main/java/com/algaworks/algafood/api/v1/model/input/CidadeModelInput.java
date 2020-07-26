package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class CidadeModelInput {
	
	@ApiModelProperty(example="Campinas", required = true)
	@NotBlank
    private String nome;
    
    @Valid
    @NotNull
    private EstadoIdModelInput estado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public EstadoIdModelInput getEstado() {
		return estado;
	}

	public void setEstado(EstadoIdModelInput estado) {
		this.estado = estado;
	}
    
}
