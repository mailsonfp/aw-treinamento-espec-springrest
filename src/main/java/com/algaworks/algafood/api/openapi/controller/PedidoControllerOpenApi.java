package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problema;
import com.algaworks.algafood.api.model.input.PedidoModelInput;
import com.algaworks.algafood.api.model.output.PedidoModelOutput;
import com.algaworks.algafood.api.model.output.PedidoResumoModelOutput;
import com.algaworks.algafood.domain.filter.PedidoModelFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {
	
    @ApiOperation(value = "Lista todos os pedidos")
    public CollectionModel<PedidoResumoModelOutput> listar();
	
	@ApiImplicitParams({
        @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation(value = "Pesquisa os pedidos usando filtros")
    public PagedModel<PedidoResumoModelOutput> listarUsandoFiltros(PedidoModelFilter filtro, Pageable pageable);
    
    @ApiOperation("Registra um pedido")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Pedido registrado"),
    })
    public PedidoModelOutput adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true)
            PedidoModelInput pedidoInput);
    
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problema.class)
    })
    public PedidoModelOutput buscar(
            @ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
            String codigoPedido);
}
