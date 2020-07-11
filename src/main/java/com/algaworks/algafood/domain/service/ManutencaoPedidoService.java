package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;

@Service
public class ManutencaoPedidoService {

	@Autowired
	EmissaoPedidoService emissaoPedidoService;
	
	@Transactional
	public void confirmarPedido(String pedidoCodigo) {
		Pedido pedido =  emissaoPedidoService.buscarThrow(pedidoCodigo);
		pedido.confirmarPedido();
	}
	
	@Transactional
	public void entregarPedido(String pedidoCodigo) {
		Pedido pedido =  emissaoPedidoService.buscarThrow(pedidoCodigo);
		pedido.entregarPedido();
	}
	
	@Transactional
	public void cancelarPedido(String pedidoCodigo) {
		Pedido pedido =  emissaoPedidoService.buscarThrow(pedidoCodigo);
		pedido.cancelarPedido();
	}
}
