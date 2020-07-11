package com.algaworks.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PedidoModelInput {
	@Valid
    @NotNull
    private RestauranteIdModelInput restaurante;
    
    @Valid
    @NotNull
    private EnderecoModelInput enderecoEntrega;
    
    @Valid
    @NotNull
    private FormaPagamentoIdModelInput formaPagamento;
    
    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoModelInput> itens;

	public RestauranteIdModelInput getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(RestauranteIdModelInput restaurante) {
		this.restaurante = restaurante;
	}

	public EnderecoModelInput getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoModelInput enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public FormaPagamentoIdModelInput getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamentoIdModelInput formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public List<ItemPedidoModelInput> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoModelInput> itens) {
		this.itens = itens;
	}
    
}
