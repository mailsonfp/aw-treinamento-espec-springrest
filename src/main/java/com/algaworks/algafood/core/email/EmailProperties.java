package com.algaworks.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {
	@NotNull
	private String remetente;
	private Sandbox sandbox = new Sandbox();
	private Implementacao impl = Implementacao.SANDBOX;
			
	public String getRemetente() {
		return remetente;
	}
	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}	
	public Sandbox getSandbox() {
		return sandbox;
	}
	public void setSandbox(Sandbox sandbox) {
		this.sandbox = sandbox;
	}
	public Implementacao getImpl() {
		return impl;
	}
	public void setImpl(Implementacao impl) {
		this.impl = impl;
	}

	public enum Implementacao {
	    SMTP, FAKE, SANDBOX
	}
	
	public class Sandbox {
		
		private String destinatario;

		public String getDestinatario() {
			return destinatario;
		}

		public void setDestinatario(String destinatario) {
			this.destinatario = destinatario;
		}
		
	}
	
}
