package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.ResturanteRepositoryCustom;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;

@Repository
public class RestauranteRepositoryImpl implements ResturanteRepositoryCustom {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
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
	
	@Override
	public List<Restaurante> findByNomeAndTaxaEntregaCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasLength(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		
		if(taxaInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial));
		}
		
		if(taxaFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal));
		}		
				
		criteria.where(predicates.toArray(new Predicate[0]));		
		criteria.orderBy(builder.desc(root.get("taxaFrete")));
		
		return manager.createQuery(criteria).getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {		
		return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeLike(nome)));
	}
}
