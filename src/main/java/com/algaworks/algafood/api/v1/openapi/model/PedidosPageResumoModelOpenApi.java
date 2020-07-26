package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.output.PedidoResumoModelOutput;

import io.swagger.annotations.ApiModel;

@ApiModel("PedidosResumoModel")
public class PedidosPageResumoModelOpenApi {
	private PedidosResumoEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	PedidosResumoEmbeddedModelOpenApi get_embedded() {
		return _embedded;
	}

	void set_embedded(PedidosResumoEmbeddedModelOpenApi _embedded) {
		this._embedded = _embedded;
	}

	Links get_links() {
		return _links;
	}

	void set_links(Links _links) {
		this._links = _links;
	}

	PageModelOpenApi getPage() {
		return page;
	}

	void setPage(PageModelOpenApi page) {
		this.page = page;
	}

	@ApiModel("PedidosResumoEmbeddedModel")
	public class PedidosResumoEmbeddedModelOpenApi {
		private List<PedidoResumoModelOutput> pedidos;

		List<PedidoResumoModelOutput> getPedidos() {
			return pedidos;
		}

		void setPedidos(List<PedidoResumoModelOutput> pedidos) {
			this.pedidos = pedidos;
		}
	}
}
