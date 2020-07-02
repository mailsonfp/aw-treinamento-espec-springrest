package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	private static final String MSG_ESTADO_EM_USO = "O estado com o código %d não pode ser excluído porque está relacionada a uma ou mais cidades.";
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não foi possível localizar um estado com o código: %d";
	@Autowired
	EstadoRepository estadoRepository;
	
	public List<Estado> listar(){
		return estadoRepository.findAll();
	}
	
	public Optional<Estado> buscar(Long estadoId) {
		return estadoRepository.findById(estadoId);
	}
	
	public Estado buscarThrow(Long estadoId) {
		return estadoRepository.findById(estadoId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)));
	}
	
	public Estado salvar(Estado estado) {		
		return estadoRepository.save(estado);
	}
	
	public void remover(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}
}
