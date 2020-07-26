package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.output.UsuarioModelOutput;

import io.swagger.annotations.ApiModel;

@ApiModel("UsuariosModel")
public class UsuariosModelOpenApi {

    private UsuariosEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    UsuariosEmbeddedModelOpenApi get_embedded() {
		return _embedded;
	}

	void set_embedded(UsuariosEmbeddedModelOpenApi _embedded) {
		this._embedded = _embedded;
	}

	Links get_links() {
		return _links;
	}

	void set_links(Links _links) {
		this._links = _links;
	}

	@ApiModel("UsuariosEmbeddedModel")
    public class UsuariosEmbeddedModelOpenApi {
        
        private List<UsuarioModelOutput> usuarios;

		List<UsuarioModelOutput> getUsuarios() {
			return usuarios;
		}

		void setUsuarios(List<UsuarioModelOutput> usuarios) {
			this.usuarios = usuarios;
		}
        
    }   
} 