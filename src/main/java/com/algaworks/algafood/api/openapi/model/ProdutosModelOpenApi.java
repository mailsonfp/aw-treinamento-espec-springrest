package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.output.ProdutoModelOutput;

import io.swagger.annotations.ApiModel;

@ApiModel("ProdutosModel")
public class ProdutosModelOpenApi {

    private ProdutosEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    ProdutosEmbeddedModelOpenApi get_embedded() {
		return _embedded;
	}

	void set_embedded(ProdutosEmbeddedModelOpenApi _embedded) {
		this._embedded = _embedded;
	}

	Links get_links() {
		return _links;
	}

	void set_links(Links _links) {
		this._links = _links;
	}

	@ApiModel("ProdutosEmbeddedModel")
    public class ProdutosEmbeddedModelOpenApi {
        
        private List<ProdutoModelOutput> produtos;

		List<ProdutoModelOutput> getProdutos() {
			return produtos;
		}

		void setProdutos(List<ProdutoModelOutput> produtos) {
			this.produtos = produtos;
		}
        
    }    
}
