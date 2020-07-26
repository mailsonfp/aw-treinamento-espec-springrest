package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.ExceptionHandler.Problema;
import com.algaworks.algafood.api.v1.model.input.CozinhaModelInput;
import com.algaworks.algafood.api.v1.model.output.CozinhaModelOutput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {
	
	@ApiOperation("Lista todas as cozinhas")
	public CollectionModel<CozinhaModelOutput> listar();
			
	@ApiOperation("Lista todas as cozinhas utilizando paginação")
	public PagedModel<CozinhaModelOutput> listarComPaginacao(Pageable pageable);
	
	@ApiOperation("Busca as cozinhas por nome")
	@ApiResponses({
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
    })
	public CollectionModel<CozinhaModelOutput> listarPorNome(@ApiParam(value = "Nome para buscar cozinhas", example = "Brasileira", required = true) String nome);	 	
	
	@ApiOperation("Busca um cozinha por id")
	@ApiResponses({
        @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problema.class),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
    })
	public CozinhaModelOutput buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);
	
	@ApiOperation("Adiciona um nova cozinha")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Cozinha cadastrada com sucesso"),
    })
	public CozinhaModelOutput adicionar(
			@ApiParam(name = "corpo", value = "Representação da cozinha com as informações para serem inseridas", required = true) CozinhaModelInput cozinhaInput);
	
	@ApiOperation("Atualiza um cozinha pelo Id")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Cozinha atualizada com sucesso"),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
    })
	public CozinhaModelOutput atualizar(@ApiParam(value = "Id d cozinha para ser atualizada", example = "1", required = true) Long cozinhaId,
			@ApiParam(name = "corpo", value = "Representação da cozinha com as informações para serem atualizadas", required = true) CozinhaModelInput cozinhaInput);
	
	@ApiOperation("Remove uma cozinha")
	@ApiResponses({
        @ApiResponse(code = 204, message = "Cozinha excluída com sucesso"),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
    })
	public void remover(@ApiParam(value = "Id da cozinha para ser excluída", example = "1", required = true) Long cozinhaId);
}
