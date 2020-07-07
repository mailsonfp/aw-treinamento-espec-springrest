package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
		
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	}
		
	public Optional<Cidade> buscar(Long cidadeId) {
	 	return cidadeRepository.findById(cidadeId);		
	}
	
	public Cidade buscarThrow(Long cidadeId) {
	 	return cidadeRepository.findById(cidadeId)
	 			.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));		
	}
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = cadastroEstadoService.buscarThrow(estadoId);
		
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
	@Transactional
	public void remover(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_CIDADE_EM_USO, cidadeId));
		}		
	}
}
