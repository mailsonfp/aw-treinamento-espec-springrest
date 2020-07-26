package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

public class UsuarioModelInputCompleto extends UsuarioModelInputDados {
	@NotBlank
    private String senha;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
