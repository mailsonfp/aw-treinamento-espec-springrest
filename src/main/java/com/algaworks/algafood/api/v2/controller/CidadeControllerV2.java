package com.algaworks.algafood.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.algaworks.algafood.api.v2.assembler.input.CidadeModelInputAssemblerV2;
import com.algaworks.algafood.api.v2.assembler.output.CidadeModelOutputAssemblerV2;
import com.algaworks.algafood.api.v2.model.input.CidadeModelInputV2;
import com.algaworks.algafood.api.v2.model.output.CidadeModelOutputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CidadeControllerV2OpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE )
public class CidadeControllerV2 implements CidadeControllerV2OpenApi{
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	CidadeModelOutputAssemblerV2 cidadeModelOut;
	
	@Autowired
	CidadeModelInputAssemblerV2 cidadeModelIn;
	
	@GetMapping
	public CollectionModel<CidadeModelOutputV2> listar(){
		return cidadeModelOut.toCollectionModel(cadastroCidadeService.listar());
	}
	
	@GetMapping("/{cidadeId}")
	public CidadeModelOutputV2 buscar(@PathVariable Long cidadeId){
		return cidadeModelOut.toModel(cadastroCidadeService.buscarThrow(cidadeId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModelOutputV2 adicionar(@RequestBody @Valid CidadeModelInputV2 cidadeInput){
		try {
			Cidade cidade = cidadeModelIn.toDomainObject(cidadeInput);
			
			return cidadeModelOut.toModel(cadastroCidadeService.salvar(cidade));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}			
	}
		
	@PutMapping("/{cidadeId}")
    public CidadeModelOutputV2 atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeModelInputV2 cidadeInput) {
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