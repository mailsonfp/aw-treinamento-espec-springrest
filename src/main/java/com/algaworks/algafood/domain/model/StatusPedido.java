package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {
	
	CRIADO("criado"),
    CONFIRMADO("confirmado", CRIADO),
    ENTREGUE("entregue", CONFIRMADO),
    CANCELADO("cancelado", CRIADO);
    
    private String descricao;
	private List<StatusPedido> statusAnteriores;
    
	private StatusPedido(String descricao, StatusPedido... statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public boolean naoPodeAlterar(StatusPedido novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
	}
}
