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
	
	public PedidosResumoEmbeddedModelOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(PedidosResumoEmbeddedModelOpenApi _embedded) {
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

	@ApiModel("PedidosResumoEmbeddedModel")
	public class PedidosResumoEmbeddedModelOpenApi {
		private List<PedidoResumoModelOutput> pedidos;

		public List<PedidoResumoModelOutput> getPedidos() {
			return pedidos;
		}

		public void setPedidos(List<PedidoResumoModelOutput> pedidos) {
			this.pedidos = pedidos;
		}
	}
}
