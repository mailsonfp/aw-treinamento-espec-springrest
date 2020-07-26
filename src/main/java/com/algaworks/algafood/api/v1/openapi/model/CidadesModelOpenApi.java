package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.output.CidadeModelOutput;

import io.swagger.annotations.ApiModel;

@ApiModel("CidadesModel")
public class CidadesModelOpenApi {
	
	private CidadeEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	public CidadeEmbeddedModelOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(CidadeEmbeddedModelOpenApi _embedded) {
		this._embedded = _embedded;
	}

	public Links get_links() {
		return _links;
	}

	public void set_links(Links _links) {
		this._links = _links;
	}

	@ApiModel("CidadesEmbeddedModel")
	public class CidadeEmbeddedModelOpenApi {
		
		private List<CidadeModelOutput> cidades;

		public List<CidadeModelOutput> getCidades() {
			return cidades;
		}

		public void setCidades(List<CidadeModelOutput> cidades) {
			this.cidades = cidades;
		}
		
	}
	
}
