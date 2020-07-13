package com.algaworks.algafood.infrastructure.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaModelFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.repository.PedidoRepositoryCustom;

@Repository
public class PedidoRepositoryImpl implements PedidoRepositoryCustom {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaModelFilter filtro, String timeOffset) {
		var criteriaBuilder = manager.getCriteriaBuilder();
		var query = criteriaBuilder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		
		var functionConvertTzDataCriacao = criteriaBuilder.function(
				"convert_tz", Date.class, root.get("dataCriacao"),
				criteriaBuilder.literal("+00:00"), criteriaBuilder.literal(timeOffset));
		
		var functionDateDataCriacao = criteriaBuilder.function(
				"date", Date.class, functionConvertTzDataCriacao);
		
		var sqlCampos = criteriaBuilder.construct(VendaDiaria.class,
				functionDateDataCriacao,
				criteriaBuilder.count(root.get("id")),
				criteriaBuilder.sum(root.get("valorTotal"))
				);
		
		query.select(sqlCampos);
		
		var predicates = new ArrayList<Predicate>();
		
		if (filtro.getRestauranteId() != null) {
		    predicates.add(criteriaBuilder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}
		    
		if (filtro.getDataCriacaoInicio() != null) {
		    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), 
		            filtro.getDataCriacaoInicio()));
		}

		if (filtro.getDataCriacaoFim() != null) {
		    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), 
		            filtro.getDataCriacaoFim()));
		}
		
		predicates.add(root.get("status").in(
		        StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		query.where(predicates.toArray(new Predicate[0]));
		
		query.groupBy(functionDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
	}

}
