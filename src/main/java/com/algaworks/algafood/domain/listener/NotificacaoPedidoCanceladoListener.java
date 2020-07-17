package com.algaworks.algafood.domain.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoPedidoCanceladoListener {
	
	@Autowired
	EnvioEmailService envioEmailService;
	
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void enviarEmailCancelamentoPedido(PedidoCanceladoEvent eventoCancelamento) {
		Pedido pedido = eventoCancelamento.getPedido();
		
		Set<String> destinatarios = retornaDestinatarios(pedido);
		
		Map<String, Object> variaveis = retornaVariaveis(pedido);
		
		var mensagem = new Mensagem(
				destinatarios,
				pedido.getRestaurante().getNome() + " - Pedido Cancelado",
				"pedido-cancelado.html",
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
}
