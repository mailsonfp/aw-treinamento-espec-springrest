package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.output.GrupoModelOutput;

import io.swagger.annotations.ApiModel;

@ApiModel("GruposModel")
public class GruposModelOpenApi {

    private GruposEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    public GruposEmbeddedModelOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(GruposEmbeddedModelOpenApi _embedded) {
		this._embedded = _embedded;
	}

	public Links get_links() {
		return _links;
	}

	public void set_links(Links _links) {
		this._links = _links;
	}

	@ApiModel("GruposEmbeddedModel")
    public class GruposEmbeddedModelOpenApi {
        
        private List<GrupoModelOutput> grupos;

		public List<GrupoModelOutput> getGrupos() {
			return grupos;
		}

		public void setGrupos(List<GrupoModelOutput> grupos) {
			this.grupos = grupos;
		}
        
    }   
}