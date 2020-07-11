package com.algaworks.algafood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	
	public Optional<Restaurante> buscar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId);		
	}
	
	public Restaurante buscarThrow(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));		
	}
	
	public List<Restaurante> listarPorTaxaEntrega(BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	public List<Restaurante> listarPorNomeId(String nome, Long cozinhaId){
		return restauranteRepository.consultarPorNomeId(nome, cozinhaId);
	}	
	
	public List<Restaurante> listarPorNomeTaxaEntrega(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.findByNomeAndTaxaEntregaCriteria(nome,taxaInicial, taxaFinal);
	}
	
	public List<Restaurante> listarComFreteGratisNome(String nome){				
		return restauranteRepository.findComFreteGratis(nome);
	}
	
	public Optional<Restaurante> buscarPrimeiro(){
		return restauranteRepository.buscarPrimeiro();
	}
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cadastroCozinhaService.buscarThrow(cozinhaId);		
		restaurante.setCozinha(cozinha);
		
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		Cidade cidade = cadastroCidadeService.buscarThrow(cidadeId);
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void remover(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(restauranteId);
		}
	}

	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarThrow(restauranteId);
		restauranteAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarThrow(restauranteId);
		restauranteAtual.inativar();
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarThrow(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarThrow(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarThrow(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarThrow(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void abrirRestaurante(Long idRestaurante) {
		Restaurante restauranteAtual = buscarThrow(idRestaurante);
		restauranteAtual.abreRestaurante();
	}
	
	@Transactional
	public void fechaRestaurante(Long idRestaurante) {
		Restaurante restauranteAtual = buscarThrow(idRestaurante);
		restauranteAtual.fechaRestaurante();
	}
	
	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
	    Restaurante restaurante = buscarThrow(restauranteId);
	    Usuario usuario = cadastroUsuarioService.buscarThrow(usuarioId);
	    
	    restaurante.adicionarResponsavel(usuario);
	}
	
	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
	    Restaurante restaurante = buscarThrow(restauranteId);
	    Usuario usuario = cadastroUsuarioService.buscarThrow(usuarioId);
	    
	    restaurante.removerResponsavel(usuario);
	}
	
	@Transactional
	public void ativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::inativar);
	}
}