package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.core.security.annotations.CheckSecurityPedido;
import com.algaworks.algafood.domain.service.PedidoManutencaoService;

@RestController
@RequestMapping(value = "/pedidos/{pedidoCodigo}")
public class PedidoManutencaoController {
	
	@Autowired
	PedidoManutencaoService manutencaoPedidoService;
	
	@CheckSecurityPedido.PermiteGerenciarPedidos
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmarPedido(@PathVariable String pedidoCodigo) {
		manutencaoPedidoService.confirmarPedido(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurityPedido.PermiteGerenciarPedidos
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregarPedido(@PathVariable String pedidoCodigo) {
		manutencaoPedidoService.entregarPedido(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurityPedido.PermiteGerenciarPedidos
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelarPedido(@PathVariable String pedidoCodigo) {
		manutencaoPedidoService.cancelarPedido(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}
}
