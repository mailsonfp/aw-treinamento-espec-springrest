package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestControllerAdvice
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@GetMapping
	public List<Estado> listar(){
		return cadastroEstadoService.listar();
	}
	
	@GetMapping("/{estadoId}")
	public Estado buscar(@PathVariable Long estadoId){
	    return cadastroEstadoService.buscarThrow(estadoId);	   
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody @Valid Estado estado) {
		return cadastroEstadoService.salvar(estado);
	}
	
	@PutMapping("/{estadoId}")
	public Estado atualizar(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {
		Estado estadoAtual = cadastroEstadoService.buscarThrow(estadoId);		
		
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		
		return cadastroEstadoService.salvar(estadoAtual);
	}    
	
	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable Long estadoId){
		cadastroEstadoService.remover(estadoId);
	}
}
