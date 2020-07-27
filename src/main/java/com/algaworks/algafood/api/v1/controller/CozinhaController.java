package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.input.CozinhaModelInputAssembler;
import com.algaworks.algafood.api.v1.assembler.output.CozinhaModelOutputAssembler;
import com.algaworks.algafood.api.v1.model.input.CozinhaModelInput;
import com.algaworks.algafood.api.v1.model.output.CozinhaModelOutput;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

//@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE) para definir que todos os métodos da classe retornam Json
@RestController
@RequestMapping("/v1/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	CozinhaModelOutputAssembler cozinhaModelOut;
	
	@Autowired
	CozinhaModelInputAssembler cozinhaModelIn;
	
	@Autowired
	PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	//@GetMapping(produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE) define qual o tipo de mídia retorna  método
	@GetMapping	
	public CollectionModel<CozinhaModelOutput>  listar(){
		return cozinhaModelOut.toCollectionModel(cadastroCozinhaService.listar());
	}
		
	@GetMapping("paginacao")	
	public PagedModel<CozinhaModelOutput> listarComPaginacao(@PageableDefault(size=2) Pageable pageable){
		Page<Cozinha> cozinhasPage = cadastroCozinhaService.listarComPaginacao(pageable);
		
		PagedModel<CozinhaModelOutput> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelOut);
		
		return cozinhasPagedModel;
	}
	
	@GetMapping("/por-nome")
	public CollectionModel<CozinhaModelOutput> listarPorNome(@RequestParam String nome){
		return cozinhaModelOut.toCollectionModel(cadastroCozinhaService.listarPorNome(nome));
	}	 	
	
	@GetMapping("/{cozinhaId}")
	public CozinhaModelOutput buscar(@PathVariable Long cozinhaId) {
		return cozinhaModelOut.toModel(cadastroCozinhaService.buscarThrow(cozinhaId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModelOutput adicionar(@RequestBody @Valid CozinhaModelInput cozinhaInput) {
		Cozinha cozinha = cozinhaModelIn.toDomainObject(cozinhaInput);
		return cozinhaModelOut.toModel(cadastroCozinhaService.salvar(cozinha));		
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaModelOutput atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaModelInput cozinhaInput){
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarThrow(cozinhaId);
		
		cozinhaModelIn.copyToDomainObject(cozinhaInput, cozinhaAtual);
		
		return cozinhaModelOut.toModel(cadastroCozinhaService.salvar(cozinhaAtual));		
	}
	
	/*
	 * @DeleteMapping("/{cozinhaId}") public ResponseEntity<?> remover(@PathVariable
	 * Long cozinhaId){ try { cadastroCozinhaService.remover(cozinhaId); return
	 * ResponseEntity.noContent().build(); }catch(EntidadeNaoEncontradaException e)
	 * { return ResponseEntity.notFound().build(); }catch(EntidadeEmUsoException e)
	 * { return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); } }
	 */
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
		cadastroCozinhaService.remover(cozinhaId);
	}
}
