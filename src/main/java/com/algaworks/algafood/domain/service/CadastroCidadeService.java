package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
		return cidadeRepository.findAll();
	}
	
	public Optional<Cidade> buscar(Long cidadeId) {
	 	return cidadeRepository.findById(cidadeId);		
	}
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		
		if (!estado.isPresent()) {
			throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar um estado com o código: %d", estadoId));
		}
		
		cidade.setEstado(estado.get());
		return cidadeRepository.save(cidade);
	}
	
	public void remover(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
		} catch (EmptyResultDataAccessException e) {
			new EntidadeNaoEncontradaException(String.format("Não foi possível localizar uma cidade com o código: %d", cidadeId));
		}
		
	}
}
