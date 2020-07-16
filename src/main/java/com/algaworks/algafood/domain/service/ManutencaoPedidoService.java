package com.algaworks.algafood.domain.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class ManutencaoPedidoService {

	@Autowired
	EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	EnvioEmailService envioEmailService;
	
	@Transactional
	public void confirmarPedido(String pedidoCodigo) {
		Pedido pedido =  emissaoPedidoService.buscarThrow(pedidoCodigo);
		pedido.confirmarPedido();
		
		Set<String> destinatarios = retornaDestinatarios(pedido);
		
		Map<String, Object> variaveis = retornaVariaveis(pedido);
		
		var mensagem = new Mensagem(
				destinatarios,
				pedido.getRestaurante().getNome() + " - Pedido confirmado",
				"pedido-confirmado.html",
				variaveis);
		
		envioEmailService.enviar(mensagem);
	}

	private Map<String, Object> retornaVariaveis(Pedido pedido) {
		Map<String, Object> variaveis = new HashMap<String, Object>();
		
		variaveis.put("pedido", pedido);
		return variaveis;
	}

	private Set<String> retornaDestinatarios(Pedido pedido) {
		Set<String> destinatarios = new HashSet<String>();
		destinatarios.add(pedido.getCliente().getEmail());
		destinatarios.add("mailsonfp@gmail.com");
		return destinatarios;
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
