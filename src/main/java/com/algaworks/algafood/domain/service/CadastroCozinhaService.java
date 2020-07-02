package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	private static final String MSG_COZINHA_EM_USO = "A cozinha com o código %d não pode ser excluída porque está relacionada a um ou mais restaurantes.";
	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não foi possível localizar uma cozinha com o código: %d";
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public List<Cozinha> listar(){
		return cozinhaRepository.findAll();
	}
	
	public Optional<Cozinha> buscar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId);		
	}
	
	public Cozinha buscarThrow(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId)));		
	}
	
	public List<Cozinha> listarPorNome(String nome){
		return cozinhaRepository.findByNomeContaining(nome);
	}
	 
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}	
	
	public void remover(Long cozinhaId){		
		try {
			cozinhaRepository.deleteById(cozinhaId);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId));
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
		
	}
}
