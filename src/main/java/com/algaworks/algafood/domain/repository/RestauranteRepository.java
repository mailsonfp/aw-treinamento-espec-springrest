package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, ResturanteRepositoryCustom {
	// pra montar um método, tem que ter o prefixo definido pelo JPA, find, query, get, count e etc
		
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	//é possível usar as key-words para concaternar campos e manipular o where, Containing= like %%, between igual o between do SQL
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	// retorna um count
	int countByCozinhaId(Long cozinha);
	
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :cozinhaId")
	List<Restaurante> consultarPorNomeId(String nome, @Param("cozinhaId") Long cozinhaId);
}
