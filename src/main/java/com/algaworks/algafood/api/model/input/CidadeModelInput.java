package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CidadeModelInput {
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
