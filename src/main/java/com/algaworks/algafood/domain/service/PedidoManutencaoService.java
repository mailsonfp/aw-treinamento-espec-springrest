package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class PedidoManutencaoService {

	@Autowired
	EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	EnvioEmailService envioEmailService;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Transactional
	public void confirmarPedido(String pedidoCodigo) {
		Pedido pedido =  emissaoPedidoService.buscarThrow(pedidoCodigo);
		pedido.confirmarPedido();
		
		pedidoRepository.save(pedido);
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
		
		pedidoRepository.save(pedido);
	}
}
