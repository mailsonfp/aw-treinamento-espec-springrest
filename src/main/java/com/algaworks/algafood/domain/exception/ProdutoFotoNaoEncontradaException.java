package com.algaworks.algafood.domain.exception;

public class ProdutoFotoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public ProdutoFotoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public ProdutoFotoNaoEncontradaException(Long restauranteId, Long produtoId) {
		this(String.format("Não foi possível localizar uma foto para o produto %d vinculado ao restaurante %d", produtoId, restauranteId));
	}
}
