package com.algaworks.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("CozinhaInput")
public class CozinhaModelInputV2 {
	
	@ApiModelProperty(example = "Italiana", required = true)
	@NotBlank
    private String nomeCozinha;

	public String getNomeCozinha() {
		return nomeCozinha;
	}

	public void setNomeCozinha(String nomeCozinha) {
		this.nomeCozinha = nomeCozinha;
	}
	
}
