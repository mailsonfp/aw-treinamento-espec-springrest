package com.algaworks.algafood.api.v2.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.ExceptionHandler.Problema;
import com.algaworks.algafood.api.v2.model.input.CidadeModelInputV2;
import com.algaworks.algafood.api.v2.model.output.CidadeModelOutputV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerV2OpenApi {

    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeModelOutputV2> listar();
    
    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problema.class),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problema.class)
    })
    CidadeModelOutputV2 buscar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId);
    
    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Cidade cadastrada"),
    })
    CidadeModelOutputV2 adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
            CidadeModelInputV2 cidadeInput);
    
    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cidade atualizada"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problema.class)
    })
    CidadeModelOutputV2 atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true) 
            Long cidadeId,
            
            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados", required = true)
            CidadeModelInputV2 cidadeInput);
    
    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Cidade excluída"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problema.class)
    })
    void remover(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId);
    
}