package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

//@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE) para definir que todos os métodos da classe retornam Json
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	//@GetMapping(produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE) define qual o tipo de mídia retorna  método
	@GetMapping	
	public List<Cozinha> listar(){
		return cadastroCozinhaService.listar();
	}
	
	/*
	 * @GetMapping("/por-nome") public List<Cozinha>
	 * listarPorNome(@RequestParam("nome") String nome){ return
	 * cadastroCozinhaService.listarPorNome(nome); }
	 */
	
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
		Optional<Cozinha> cozinha = cadastroCozinhaService.buscar(cozinhaId);
		if(!cozinha.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cozinha.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinhaService.salvar(cozinha);		
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha){
		Optional<Cozinha> cozinhaAtual = cadastroCozinhaService.buscar(cozinhaId);
				
		if(!cozinhaAtual.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
		
		Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual.get());
		return ResponseEntity.ok(cozinhaSalva);	
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<?> remover(@PathVariable Long cozinhaId){
		try {
			cadastroCozinhaService.remover(cozinhaId);
			return ResponseEntity.noContent().build();
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		
	}
}
