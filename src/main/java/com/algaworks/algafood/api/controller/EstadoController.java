package com.algaworks.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.algaworks.algafood.api.assembler.input.EstadoModelInputAssembler;
import com.algaworks.algafood.api.assembler.output.EstadoModelOutputAssembler;
import com.algaworks.algafood.api.model.input.EstadoModelInput;
import com.algaworks.algafood.api.model.output.EstadoModelOutput;
import com.algaworks.algafood.core.security.annotations.CheckSecurityEstado;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestControllerAdvice
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Autowired
	EstadoModelInputAssembler estadoModelIn;
	
	@Autowired
	EstadoModelOutputAssembler estadoModelOut;
	
	@CheckSecurityEstado.PermiteConsultar
	@GetMapping
	public CollectionModel<EstadoModelOutput> listar(){
		return estadoModelOut.toCollectionModel(cadastroEstadoService.listar());
	}
	
	@CheckSecurityEstado.PermiteConsultar
	@GetMapping("/{estadoId}")
	public EstadoModelOutput buscar(@PathVariable Long estadoId){
	    return estadoModelOut.toModel(cadastroEstadoService.buscarThrow(estadoId));	   
	}
	
	@CheckSecurityEstado.PermiteEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModelOutput adicionar(@RequestBody @Valid EstadoModelInput estadoInput) {
		Estado estado = estadoModelIn.toDomainObject(estadoInput);
		return estadoModelOut.toModel(cadastroEstadoService.salvar(estado));
	}
	
	@CheckSecurityEstado.PermiteEditar
	@PutMapping("/{estadoId}")
	public EstadoModelOutput atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoModelInput estadoInput) {
		Estado estadoAtual = cadastroEstadoService.buscarThrow(estadoId);		
		
		estadoModelIn.copyToDomainObject(estadoInput, estadoAtual);
		
		return estadoModelOut.toModel(cadastroEstadoService.salvar(estadoAtual));
	}    
	
	@CheckSecurityEstado.PermiteEditar
	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable Long estadoId){
		cadastroEstadoService.remover(estadoId);
	}
}
