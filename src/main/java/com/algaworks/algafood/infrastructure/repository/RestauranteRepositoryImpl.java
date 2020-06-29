package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ResturanteRepositoryCustom;

@Repository
public class RestauranteRepositoryImpl implements ResturanteRepositoryCustom {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> findByNomeAndTaxaEntrega(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		
		var jpql = new StringBuilder();
		jpql.append("from Restaurante where id != null ");
		
		var parametros = new HashMap<String, Object>();
		
		if (StringUtils.hasLength(nome)) {
			jpql.append("and nome like :nome ");
			parametros.put("nome", "%" + nome + "%");
		}
		
		if(taxaInicial != null) {
			jpql.append("and taxaFrete >= :taxaInicial ");
			parametros.put("taxaInicial", taxaInicial);
		}
		
		if(taxaFinal != null) {
			jpql.append("and taxaFrete <= :taxaFinal ");
			parametros.put("taxaFinal", taxaFinal);
		}
		
		TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);		
		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
		
		return query.getResultList();		
	}
}
