package com.algaworks.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.ProdutoFoto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryCustom;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryCustom {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	@Override
	public ProdutoFoto save(ProdutoFoto produtoFoto) {
		return manager.merge(produtoFoto);
	}
	
	@Transactional
	@Override
	public void removeFoto(ProdutoFoto produtoFoto) {
		manager.remove(produtoFoto);
	}

}
