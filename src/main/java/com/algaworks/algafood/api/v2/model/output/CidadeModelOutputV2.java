package com.algaworks.algafood.api.v2.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;

@Relation(collectionRelation = "CidadesModelOutput")
//@ApiModel(value="Cidade", description = "Representa o retorno de uma cidade")
public class CidadeModelOutputV2 extends RepresentationModel<CidadeModelOutputV2> {
	
	@ApiModelProperty(value = "Id da cidade", example = "1")
	private Long idCidade;
	
	@ApiModelProperty(example = "Campinas")
    private String nomeCidade;
    
	private Long idEstado;
	
	private String nomeEstado;

	public Long getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}
	
}
