package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.output.FormaPagamentoModelOutput;

import io.swagger.annotations.ApiModel;

@ApiModel("FormasPagamentoModel")
public class FormasPagamentoModelOpenApi {

    private FormasPagamentoEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    public FormasPagamentoEmbeddedModelOpenApi get_embedded() {
		return _embedded;
	}

	public void set_embedded(FormasPagamentoEmbeddedModelOpenApi _embedded) {
		this._embedded = _embedded;
	}

	public Links get_links() {
		return _links;
	}

	public void set_links(Links _links) {
		this._links = _links;
	}

	@ApiModel("FormasPagamentoEmbeddedModel")
    public class FormasPagamentoEmbeddedModelOpenApi {
        
        private List<FormaPagamentoModelOutput> formasPagamento;

		public List<FormaPagamentoModelOutput> getFormasPagamento() {
			return formasPagamento;
		}

		public void setFormasPagamento(List<FormaPagamentoModelOutput> formasPagamento) {
			this.formasPagamento = formasPagamento;
		}
        
    }   
}