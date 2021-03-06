package com.algaworks.algafood.api.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.algaworks.algafood.api.assembler.input.CozinhaModelInputAssembler;
import com.algaworks.algafood.api.assembler.output.CozinhaModelOutputAssembler;
import com.algaworks.algafood.api.model.input.CozinhaModelInput;
import com.algaworks.algafood.api.model.output.CozinhaModelOutput;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.core.security.annotations.CheckSecurityCozinhas;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

//@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE) para definir que todos os métodos da classe retornam Json
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {	
	
	private static final Logger logger = LoggerFactory.getLogger(CozinhaController.class);
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	CozinhaModelOutputAssembler cozinhaModelOut;
	
	@Autowired
	CozinhaModelInputAssembler cozinhaModelIn;
	
	@Autowired
	PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	@CheckSecurityCozinhas.PermiteConsultar
	@GetMapping	
	public CollectionModel<CozinhaModelOutput>  listar(){
		logger.info("Listando cozinhas sem paginação");
		logger.info(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		return cozinhaModelOut.toCollectionModel(cadastroCozinhaService.listar());
	}
	
	@CheckSecurityCozinhas.PermiteConsultar
	@GetMapping("paginacao")	
	public PagedModel<CozinhaModelOutput> listarComPaginacao(@PageableDefault(size=2) Pageable pageable){
		logger.info("Listando cozinhas com paginação");
		Page<Cozinha> cozinhasPage = cadastroCozinhaService.listarComPaginacao(pageable);
		
		PagedModel<CozinhaModelOutput> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelOut);
		
		return cozinhasPagedModel;
	}
	
	@CheckSecurityCozinhas.PermiteConsultar
	@GetMapping("/por-nome")
	public CollectionModel<CozinhaModelOutput> listarPorNome(@RequestParam String nome){
		return cozinhaModelOut.toCollectionModel(cadastroCozinhaService.listarPorNome(nome));
	}	 	
	
	@CheckSecurityCozinhas.PermiteConsultar
	@GetMapping("/{cozinhaId}")
	public CozinhaModelOutput buscar(@PathVariable Long cozinhaId) {
		return cozinhaModelOut.toModel(cadastroCozinhaService.buscarThrow(cozinhaId));
	}
	
	@CheckSecurityCozinhas.PermiteEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModelOutput adicionar(@RequestBody @Valid CozinhaModelInput cozinhaInput) {
		Cozinha cozinha = cozinhaModelIn.toDomainObject(cozinhaInput);
		return cozinhaModelOut.toModel(cadastroCozinhaService.salvar(cozinha));		
	}
	
	@CheckSecurityCozinhas.PermiteEditar
	@PutMapping("/{cozinhaId}")
	public CozinhaModelOutput atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaModelInput cozinhaInput){
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
