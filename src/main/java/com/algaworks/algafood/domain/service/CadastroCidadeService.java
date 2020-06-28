package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Cidade> listar(){
		return cidadeRepository.listar();
	}
	
	public Cidade buscar(Long cidadeId) {
		Cidade cidade = cidadeRepository.buscar(cidadeId);
		
		if(cidade==null) {
			throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar a cidade com o código: %d", cidadeId));
		}
		
		return cidade;
	}
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);
		
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar um estado com o código: %d", estadoId));
		}
		
		cidade.setEstado(estado);
		return cidadeRepository.salvar(cidade);
	}
	
	public void remover(Long cidadeId) {
		Cidade cidade = cidadeRepository.buscar(cidadeId);
		if(cidade==null) {
			throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar a cidade com o código: %d", cidadeId));
		}
		
		cidadeRepository.remover(cidade);
	}
}
