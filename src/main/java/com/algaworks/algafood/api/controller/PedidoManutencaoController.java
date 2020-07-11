package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.service.ManutencaoPedidoService;

@RestController
@RequestMapping(value = "/pedidos/{pedidoCodigo}")
public class PedidoManutencaoController {
	
	@Autowired
	ManutencaoPedidoService manutencaoPedidoService;
	
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmarPedido(@PathVariable String pedidoCodigo) {
		manutencaoPedidoService.confirmarPedido(pedidoCodigo);
	}
	
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregarPedido(@PathVariable String pedidoCodigo) {
		manutencaoPedidoService.entregarPedido(pedidoCodigo);
	}
	
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelarPedido(@PathVariable String pedidoCodigo) {
		manutencaoPedidoService.cancelarPedido(pedidoCodigo);
	}
}
