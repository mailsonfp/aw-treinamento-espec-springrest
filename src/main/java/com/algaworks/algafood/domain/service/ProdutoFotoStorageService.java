package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

public interface ProdutoFotoStorageService {
	
	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeArquivo);
	
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
		private InputStream inputStream;				
		
		public NovaFoto(String nomeArquivo, InputStream inputStream) {
			super();
			this.nomeArquivo = nomeArquivo;
			this.inputStream = inputStream;
		}
		public String getNomeArquivo() {
			return nomeArquivo;
		}
		public void setNomeArquivo(String nomeArquivo) {
			this.nomeArquivo = nomeArquivo;
		}
		public InputStream getInputStream() {
			return inputStream;
		}
		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}
		
	}
}
