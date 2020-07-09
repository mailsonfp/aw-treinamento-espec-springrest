package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {
	
	@Autowired
	FormaPagamentoRepository formaPagamentoRepository;
	
	public List<FormaPagamento> listar(){
		return formaPagamentoRepository.findAll();
	}
	
	public Optional<FormaPagamento> buscar(Long idFormaPagamento) {
		return formaPagamentoRepository.findById(idFormaPagamento);
	}
	
	public FormaPagamento buscarThrow(Long idFormaPagamento) {
		return formaPagamentoRepository.findById(idFormaPagamento)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(idFormaPagamento));
	}
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	@Transactional
	public void remover(Long idFormaPagamento) {
		try {
			formaPagamentoRepository.deleteById(idFormaPagamento);
			formaPagamentoRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(idFormaPagamento);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A forma de pagamento %d não pode ser excluída porque está relacionada a uma ou mais entidades.", idFormaPagamento));
		}
	}
}
