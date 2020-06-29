package com.algaworks.algafood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	
	public List<Restaurante> listarPorTaxaEntrega(BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	public List<Restaurante> listarPorNomeId(String nome, Long cozinhaId){
		return restauranteRepository.consultarPorNomeId(nome, cozinhaId);
	}

	public Optional<Restaurante> buscar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId);		
	}
	
	public List<Restaurante> listarPorNomeTaxaEntrega(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.findByNomeAndTaxaEntregaCriteria(nome,taxaInicial, taxaFinal);
	}
	
	public List<Restaurante> listarComFreteGratisNome(String nome){				
		return restauranteRepository.findComFreteGratis(nome);
	}
	
	public Optional<Restaurante> buscarPrimeiro(){
		return restauranteRepository.buscarPrimeiro();
	}
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi possível localizar uma cozinha com o código: %d", cozinhaId)));		
		
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}
	
	public void remover(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar um restaurante com o código: %d", restauranteId));
		}
	}
}
