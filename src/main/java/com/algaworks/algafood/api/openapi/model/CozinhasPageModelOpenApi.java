package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.output.CozinhaModelOutput;

import io.swagger.annotations.ApiModel;

@ApiModel("Cozinhas Modelo")
public class CozinhasPageModelOpenApi{
	private CozinhasEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
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

	public PageModelOpenApi getPage() {
		return page;
	}

	public void setPage(PageModelOpenApi page) {
		this.page = page;
	}

	@ApiModel("CozinhasEmbeddedModel")
	public class CozinhasEmbeddedModelOpenApi {
		
		private List<CozinhaModelOutput> cozinhas;

		public List<CozinhaModelOutput> getCozinhas() {
			return cozinhas;
		}

		public void setCozinhas(List<CozinhaModelOutput> cozinhas) {
			this.cozinhas = cozinhas;
		}
		
	}
}
