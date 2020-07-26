package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.ExceptionHandler.Problema;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoModelInput;
import com.algaworks.algafood.api.v1.model.output.FormaPagamentoModelOutput;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {
	
	@ApiOperation(value = "Lista todas as formas de pagamento", response = FormasPagamentoModelOpenApi.class)
    public List<FormaPagamentoModelOutput> listar();
	
	@ApiOperation("Lista todas as formas de pagamento utilizando cache")
    public ResponseEntity<List<FormaPagamentoModelOutput>> listarComCash();
    
	@ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problema.class),
        @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problema.class)
    })
    public FormaPagamentoModelOutput buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
	
	@ApiOperation("Busca uma forma de pagamento por ID utilizando cache")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problema.class),
        @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problema.class)
    })
    public ResponseEntity<FormaPagamentoModelOutput> buscarComCache(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
    
	@ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Forma de pagamento cadastrada com sucesso", response=Problema.class),
    })
    public FormaPagamentoModelOutput adicionar(
    		@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true) FormaPagamentoModelInput formaPagamentoInput);
    
	@ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
        @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problema.class)
    })
    public FormaPagamentoModelOutput atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId,
    		@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados", required = true) FormaPagamentoModelInput formaPagamentoInput);
	
	@ApiOperation("Exclui uma forma de pagamento por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Forma de pagamento excluída"),
        @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problema.class)
    })
    public void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
