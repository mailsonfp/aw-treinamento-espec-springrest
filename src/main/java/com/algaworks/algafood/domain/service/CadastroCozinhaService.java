package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	private static final String MSG_COZINHA_EM_USO = "A cozinha com o código %d não pode ser excluída porque está relacionada a um ou mais restaurantes.";

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public List<Cozinha> listar(){
		return cozinhaRepository.findAll();
	}
	
	public Page<Cozinha> listarComPaginacao(Pageable pageable){
		return cozinhaRepository.findAll(pageable);
	}
	
	public Optional<Cozinha> buscar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId);		
	}
	
	public Cozinha buscarThrow(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));		
	}
	
	public List<Cozinha> listarPorNome(String nome){
		return cozinhaRepository.findByNomeContaining(nome);
	}
	 
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}	
	
	@Transactional
	public void remover(Long cozinhaId){		
		try {
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
		
	}
}
