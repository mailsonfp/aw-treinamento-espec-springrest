package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface ResturanteRepositoryCustom {

	List<Restaurante> findByNomeAndTaxaEntrega(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> findByNomeAndTaxaEntregaCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> findComFreteGratis(String nome);
	
}