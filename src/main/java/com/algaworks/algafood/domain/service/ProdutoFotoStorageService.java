package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

public interface ProdutoFotoStorageService {
	
	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeArquivo);
	
	FotoRecuperada recuperar(String nomeArquivo);
	
	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() +"_"+ nomeOriginal;
	}
	
	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);
		
		if(nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}
	
	public class NovaFoto{
		
		private String nomeArquivo;
		private String contentType;
		private InputStream inputStream;				
				
		public NovaFoto(String nomeArquivo, String contentType, InputStream inputStream) {
			super();
			this.nomeArquivo = nomeArquivo;
			this.contentType = contentType;
			this.inputStream = inputStream;
		}
		public String getNomeArquivo() {
			return nomeArquivo;
		}
		public void setNomeArquivo(String nomeArquivo) {
			this.nomeArquivo = nomeArquivo;
		}		
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		public InputStream getInputStream() {
			return inputStream;
		}
		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}		
	}
	
	class FotoRecuperada{
		private InputStream inputStream;
		private String url;
			
		public FotoRecuperada() {
			
		}
		
		public FotoRecuperada(InputStream inputStream) {
			super();
			this.inputStream = inputStream;
		}
		
		public FotoRecuperada(String url) {
			super();
			this.url = url;
		}

		public InputStream getInputStream() {
			return inputStream;
		}
		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}		
		
		public boolean temUrl() {
			return url != null;
		}
	}
}
