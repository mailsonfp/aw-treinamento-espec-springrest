package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		return restauranteRepository.listar();
	}
	
	public Restaurante buscar(Long restauranteId) {
		Restaurante restaurante = restauranteRepository.buscar(restauranteId);
		if(restaurante == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar um restaurante com o código: %d", restauranteId));
		}
		return restaurante;
	}
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		
		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar uma cozinha com o código: %d", cozinhaId));
		}
		
		restaurante.setCozinha(cozinha);
		return restauranteRepository.salvar(restaurante);
	}
	
	public void remover(Long restauranteId) {
		Restaurante restaurante = restauranteRepository.buscar(restauranteId);
		if(restaurante==null) {
			throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar um restaurante com o código: %d", restauranteId));
		}
		
		restauranteRepository.remover(restaurante);
	}
}
