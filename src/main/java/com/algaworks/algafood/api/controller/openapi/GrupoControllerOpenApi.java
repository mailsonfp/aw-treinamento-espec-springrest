package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import com.algaworks.algafood.api.ExceptionHandler.Problema;
import com.algaworks.algafood.api.model.input.GrupoModelInput;
import com.algaworks.algafood.api.model.output.GrupoModelOutput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {
	
	@ApiOperation("Lista os grupos")
    public List<GrupoModelOutput> listar();
    
    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problema.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problema.class)
    })
    public GrupoModelOutput buscar(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long grupoId);
    
    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    public GrupoModelOutput adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo grupo")
            GrupoModelInput grupoInput);
    
    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Grupo atualizado"),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problema.class)
    })
    public GrupoModelOutput atualizar(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long grupoId,
            
            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados")
            GrupoModelInput grupoInput);
    
    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Grupo excluído"),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problema.class)
    })
    
    public void remover(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long grupoId);
}
