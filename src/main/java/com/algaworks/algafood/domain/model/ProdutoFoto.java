package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class ProdutoFoto {
	
	@Id
	@Column(name = "produto_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Produto produto;
	
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
	public ProdutoFoto() {
		
	}
	
	public ProdutoFoto(Produto produto, String nomeArquivo, String descricao, String contentType, Long tamanho) {
		super();
		this.produto = produto;
		this.nomeArquivo = nomeArquivo;
		this.descricao = descricao;
		this.contentType = contentType;
		this.tamanho = tamanho;
	}

	public Long getoId() {
		return id;
	}
	public void setProdutoId(Long id) {
		this.id = id;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Long getTamanho() {
		return tamanho;
	}
	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}
	
	public Long getRestauranteId() {
		if(getProduto() != null) {
			return getProduto().getRestaurante().getId();
		}
		
		return null;
	}
}
