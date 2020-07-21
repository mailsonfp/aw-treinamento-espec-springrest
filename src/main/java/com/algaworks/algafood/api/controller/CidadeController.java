package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.input.CidadeModelInputAssembler;
import com.algaworks.algafood.api.assembler.output.CidadeModelOutputAssembler;
import com.algaworks.algafood.api.model.input.CidadeModelInput;
import com.algaworks.algafood.api.model.output.CidadeModelOutput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE )
public class CidadeController implements CidadeControllerOpenApi {
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	CidadeModelOutputAssembler cidadeModelOut;
	
	@Autowired
	CidadeModelInputAssembler cidadeModelIn;
	
	@GetMapping
	public List<CidadeModelOutput> listar(){
		return cidadeModelOut.toCollectionModel(cadastroCidadeService.listar());
	}
	
	@GetMapping("/{cidadeId}")
	public CidadeModelOutput buscar(@PathVariable Long cidadeId){
		return cidadeModelOut.toModel(cadastroCidadeService.buscarThrow(cidadeId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModelOutput adicionar(@RequestBody @Valid CidadeModelInput cidadeInput){
		try {
			Cidade cidade = cidadeModelIn.toDomainObject(cidadeInput);
			return cidadeModelOut.toModel(cadastroCidadeService.salvar(cidade));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}			
	}
		
	@PutMapping("/{cidadeId}")
    public CidadeModelOutput atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeModelInput cidadeInput) {
		Cidade cidadeAtual = cadastroCidadeService.buscarThrow(cidadeId);
		cidadeModelIn.copyToDomainObject(cidadeInput, cidadeAtual);
		
		try {
			return cidadeModelOut.toModel(cadastroCidadeService.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}							
    }
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId){
		cadastroCidadeService.remover(cidadeId);
	}	
}
