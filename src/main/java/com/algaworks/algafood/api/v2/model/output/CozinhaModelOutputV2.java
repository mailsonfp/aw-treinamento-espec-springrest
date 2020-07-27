package com.algaworks.algafood.api.v2.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;

@Relation(collectionRelation = "CozinhasModelOutput")
public class CozinhaModelOutputV2 extends RepresentationModel<CozinhaModelOutputV2> {
	
	@ApiModelProperty(example = "1")
	private Long idCozinha;
	
	@ApiModelProperty(example = "Italiana")
	private String nomeCozinha;

	public Long getIdCozinha() {
		return idCozinha;
	}

	public void setIdCozinha(Long idCozinha) {
		this.idCozinha = idCozinha;
	}

	public String getNomeCozinha() {
		return nomeCozinha;
	}

	public void setNomeCozinha(String nomeCozinha) {
		this.nomeCozinha = nomeCozinha;
	}
	
}
