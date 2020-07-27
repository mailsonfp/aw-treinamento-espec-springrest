package com.algaworks.algafood.api.v2.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.ExceptionHandler.Problema;
import com.algaworks.algafood.api.v2.model.input.CozinhaModelInputV2;
import com.algaworks.algafood.api.v2.model.output.CozinhaModelOutputV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerV2OpenApi {
	
	@ApiOperation("Lista as cozinhas")
	public CollectionModel<CozinhaModelOutputV2>  listar();
	
    @ApiOperation("Lista as cozinhas com paginação")
    PagedModel<CozinhaModelOutputV2> listarComPaginacao(Pageable pageable);
    
    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problema.class),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
    })
    CozinhaModelOutputV2 buscar(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId);
    
    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Cozinha cadastrada"),
    })
    CozinhaModelOutputV2 adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true)
            CozinhaModelInputV2 cozinhaInput);
    
    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cozinha atualizada"),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
    })
    CozinhaModelOutputV2 atualizar(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId,
            
            @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados", 
                required = true)
            CozinhaModelInputV2 cozinhaInput);
    
    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Cozinha excluída"),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problema.class)
    })
    void remover(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId);
    
}
