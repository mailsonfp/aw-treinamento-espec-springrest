package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.output.PermissaoModelOutput;

import io.swagger.annotations.ApiModel;

@ApiModel("PermissoesModel")
public class PermissoesModelOpenApi {

    private PermissoesEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    public PermissoesEmbeddedModelOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(PermissoesEmbeddedModelOpenApi _embedded) {
		this._embedded = _embedded;
	}

	public Links get_links() {
		return _links;
	}

	public void set_links(Links _links) {
		this._links = _links;
	}

	@ApiModel("PermissoesEmbeddedModel")
    public class PermissoesEmbeddedModelOpenApi {
        
        private List<PermissaoModelOutput> permissoes;

		public List<PermissaoModelOutput> getPermissoes() {
			return permissoes;
		}

		public void setPermissoes(List<PermissaoModelOutput> permissoes) {
			this.permissoes = permissoes;
		}
        
    }   
}