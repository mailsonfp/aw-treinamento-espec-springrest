package com.algaworks.algafood.domain.service;

import java.util.Map;
import java.util.Set;

public interface EnvioEmailService {
	
	void enviar(Mensagem mensagem);
	
	class Mensagem {
		
		private Set<String> destinatarios;
		private String assunto;
		private String corpo;
		private Map<String, Object> variaveisEmail;
		
		public Mensagem(Set<String> destinatarios, String assunto, String corpo, Map<String, Object> variaveisEmail) {
			super();
			this.destinatarios = destinatarios;
			this.assunto = assunto;
			this.corpo = corpo;
			this.variaveisEmail = variaveisEmail;
		}
		public Set<String> getDestinatarios() {
			return destinatarios;
		}
		public void setDestinatarios(Set<String> destinatarios) {
			this.destinatarios = destinatarios;
		}
		public String getAssunto() {
			return assunto;
		}
		public void setAssunto(String assunto) {
			this.assunto = assunto;
		}
		public String getCorpo() {
			return corpo;
		}
		public void setCorpo(String corpo) {
			this.corpo = corpo;
		}
		public Map<String, Object> getVariaveisEmail() {
			return variaveisEmail;
		}
		public void setVariaveisEmail(Map<String, Object> variaveisEmail) {
			this.variaveisEmail = variaveisEmail;
		}		
	}
}
