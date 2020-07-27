package com.algaworks.algafood.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v2.model.output.CozinhaModelOutputV2;

import io.swagger.annotations.ApiModel;

@ApiModel("CozinhasModelOutput")
public class CozinhasModelOutputV2OpenApi {
	
	private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApiV2 page;
    
    public CozinhasEmbeddedModelOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(CozinhasEmbeddedModelOpenApi _embedded) {
		this._embedded = _embedded;
	}

	public Links get_links() {
		return _links;
	}

	public void set_links(Links _links) {
		this._links = _links;
	}

	public PageModelOpenApiV2 getPage() {
		return page;
	}

	public void setPage(PageModelOpenApiV2 page) {
		this.page = page;
	}

	@ApiModel("CozinhasEmbeddedModel")
    public class CozinhasEmbeddedModelOpenApi {
        
        private List<CozinhaModelOutputV2> cozinhas;

		public List<CozinhaModelOutputV2> getCozinhas() {
			return cozinhas;
		}

		public void setCozinhas(List<CozinhaModelOutputV2> cozinhas) {
			this.cozinhas = cozinhas;
		}
        
    }
}
