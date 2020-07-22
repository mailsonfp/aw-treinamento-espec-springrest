package com.algaworks.algafood.api.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;

@Relation(collectionRelation = "cidades")
//@ApiModel(value="Cidade", description = "Representa o retorno de uma cidade")
public class CidadeModelOutput extends RepresentationModel<CidadeModelOutput> {
	
	@ApiModelProperty(value = "Id da cidade", example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Campinas")
    private String nome;
    private EstadoModelOutput estado;
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
	public EstadoModelOutput getEstado() {
		return estado;
	}
	public void setEstado(EstadoModelOutput estado) {
		this.estado = estado;
	}
    
}
