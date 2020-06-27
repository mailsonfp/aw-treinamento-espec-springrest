package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	EstadoRepository estadoRepository;
	
	public List<Estado> listar(){
		return estadoRepository.listar();
	}
	
	public Estado buscar(Long estadoId) {
		Estado estado = estadoRepository.buscar(estadoId);
		if(estado==null) {
			throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar um estado com o código: %d", estadoId));
		}
		
		return estado;
	}
	
	public Estado salvar(Estado estado) {		
		return estadoRepository.salvar(estado);
	}
	
	public void remover(Long estadoId) {
		Estado estado = estadoRepository.buscar(estadoId);
		if(estado==null) {
			throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar um estado com o código: %d", estadoId));
		}
		
		try {
			estadoRepository.remover(estado);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O estado com o código: %d não pode ser excluído porque está relacionada a uma ou mais cidades.", estadoId));
		}
	}
}
