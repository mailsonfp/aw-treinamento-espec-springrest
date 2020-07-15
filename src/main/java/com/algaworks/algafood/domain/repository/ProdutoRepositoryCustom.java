package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.ProdutoFoto;

public interface ProdutoRepositoryCustom {
	
	ProdutoFoto save(ProdutoFoto produtoFoto);
	
	void removeFoto(ProdutoFoto produtoFoto);
}
