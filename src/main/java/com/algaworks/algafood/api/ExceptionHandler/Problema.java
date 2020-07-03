package com.algaworks.algafood.api.ExceptionHandler;

import java.time.LocalDateTime;

public class Problema {
	
	private LocalDateTime dataHora;
	private String mensagem;
	
	public Problema(LocalDateTime dataHora, String mensagem) {
		super();
		this.dataHora = dataHora;
		this.mensagem = mensagem;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
