package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.ProdutoFotoNaoEncontradaException;
import com.algaworks.algafood.domain.model.ProdutoFoto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.ProdutoFotoStorageService.NovaFoto;

@Service
public class CadastroProdutoFotoService {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoFotoStorageService fotoStorageService;
	
	public ProdutoFoto buscarThrow(Long restauranteId, Long produtoId) {
	    return produtoRepository.findFotoById(restauranteId, produtoId)
	            .orElseThrow(() -> new ProdutoFotoNaoEncontradaException(restauranteId, produtoId));
	}
	
	@Transactional
	public ProdutoFoto salvar(ProdutoFoto produtoFoto, InputStream dadosArquivo) {
		Long restauranteId = produtoFoto.getRestauranteId();
		Long produtoId = produtoFoto.getProduto().getId();
		String novoNomeArquivo = fotoStorageService.gerarNomeArquivo(produtoFoto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<ProdutoFoto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if(fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.removeFoto(fotoExistente.get());			
		}
		
		produtoFoto.setNomeArquivo(novoNomeArquivo);
		produtoFoto = produtoRepository.save(produtoFoto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = new NovaFoto(produtoFoto.getNomeArquivo(), produtoFoto.getContentType(), dadosArquivo);
				
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
		
		return produtoFoto;
	}
	
	@Transactional
	public void remover(Long restauranteId, Long produtoId) {
		ProdutoFoto produtoFoto = buscarThrow(restauranteId, produtoId);
		String nomeArquivo = produtoFoto.getNomeArquivo();
		
		produtoRepository.removeFoto(produtoFoto);
		produtoRepository.flush();
		
		fotoStorageService.remover(nomeArquivo);
	}
}
