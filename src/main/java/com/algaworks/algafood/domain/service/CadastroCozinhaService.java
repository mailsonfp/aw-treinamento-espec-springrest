package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public List<Cozinha> listar(){
		return cozinhaRepository.listar();
	}
	
	public List<Cozinha> listarPorNome(String nome){
		return cozinhaRepository.consultarPorNome(nome);
	}
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.salvar(cozinha);
	}
	
	public Cozinha buscar(Long cozinhaId) {
		return cozinhaRepository.buscar(cozinhaId);
	}
	
	public void remover(Long cozinhaId){		
		try {
			Cozinha cozinhaAtual = buscar(cozinhaId);
			if(cozinhaAtual==null) {
				throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar uma cozinha com o código: %d", cozinhaId));
			}
			cozinhaRepository.remover(cozinhaAtual);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A cozinha com o código: %d não pode ser excluída porque está relacionada a um ou mais restaurantes.", cozinhaId));
		}
		
	}
}
