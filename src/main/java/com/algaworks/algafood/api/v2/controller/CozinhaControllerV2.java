package com.algaworks.algafood.api.v2.controller;

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

import com.algaworks.algafood.api.v2.assembler.input.CozinhaModelInputAssemblerV2;
import com.algaworks.algafood.api.v2.assembler.output.CozinhaModelOutputAssemblerV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaModelInputV2;
import com.algaworks.algafood.api.v2.model.output.CozinhaModelOutputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CozinhaControllerV2OpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

//@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE) para definir que todos os métodos da classe retornam Json
@RestController
@RequestMapping("/v2/cozinhas")
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi{	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	CozinhaModelOutputAssemblerV2 cozinhaModelOut;
	
	@Autowired
	CozinhaModelInputAssemblerV2 cozinhaModelIn;
	
	@Autowired
	PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	//@GetMapping(produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE) define qual o tipo de mídia retorna  método
	@GetMapping	
	public CollectionModel<CozinhaModelOutputV2>  listar(){
		return cozinhaModelOut.toCollectionModel(cadastroCozinhaService.listar());
	}
		
	@GetMapping("paginacao")	
	public PagedModel<CozinhaModelOutputV2> listarComPaginacao(@PageableDefault(size=2) Pageable pageable){
		Page<Cozinha> cozinhasPage = cadastroCozinhaService.listarComPaginacao(pageable);
		
		PagedModel<CozinhaModelOutputV2> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelOut);
		
		return cozinhasPagedModel;
	}
	
	@GetMapping("/por-nome")
	public CollectionModel<CozinhaModelOutputV2> listarPorNome(@RequestParam String nome){
		return cozinhaModelOut.toCollectionModel(cadastroCozinhaService.listarPorNome(nome));
	}	 	
	
	@GetMapping("/{cozinhaId}")
	public CozinhaModelOutputV2 buscar(@PathVariable Long cozinhaId) {
		return cozinhaModelOut.toModel(cadastroCozinhaService.buscarThrow(cozinhaId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModelOutputV2 adicionar(@RequestBody @Valid CozinhaModelInputV2 cozinhaInput) {
		Cozinha cozinha = cozinhaModelIn.toDomainObject(cozinhaInput);
		return cozinhaModelOut.toModel(cadastroCozinhaService.salvar(cozinha));		
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaModelOutputV2 atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaModelInputV2 cozinhaInput){
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarThrow(cozinhaId);
		
		cozinhaModelIn.copyToDomainObject(cozinhaInput, cozinhaAtual);
		
		return cozinhaModelOut.toModel(cadastroCozinhaService.salvar(cozinhaAtual));		
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
		cadastroCozinhaService.remover(cozinhaId);
	}
}
