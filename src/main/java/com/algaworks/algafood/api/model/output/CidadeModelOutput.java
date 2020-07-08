package com.algaworks.algafood.api.model.output;

public class CidadeModelOutput {
	private Long id;
    private String nome;
    private EstadoModelOutput estado;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public EstadoModelOutput getEstado() {
		return estado;
	}
	public void setEstado(EstadoModelOutput estado) {
		this.estado = estado;
	}
    
}
