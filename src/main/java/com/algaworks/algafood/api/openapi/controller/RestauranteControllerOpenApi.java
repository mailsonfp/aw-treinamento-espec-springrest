package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.ExceptionHandler.Problema;
import com.algaworks.algafood.api.model.input.RestauranteModelInput;
import com.algaworks.algafood.api.model.output.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.model.output.RestauranteBasicoModel;
import com.algaworks.algafood.api.model.output.RestauranteModelOutput;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {
	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
                name = "projecao", paramType = "query", type = "string")
    })
    public CollectionModel<RestauranteModelOutput> listar();
	
	
	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "resumo",
                name = "projecao", paramType = "query", type = "string")
    })
    public CollectionModel<RestauranteBasicoModel> listarComResumo();
    
	@ApiIgnore
    @ApiOperation(value = "Lista restaurantes", hidden = true)
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNome();
    
    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problema.class),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problema.class)
    })
    public RestauranteModelOutput buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Restaurante cadastrado"),
    })
    public RestauranteModelOutput adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
            RestauranteModelInput restauranteInput);
    
    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Restaurante atualizado"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problema.class)
    })
    public RestauranteModelOutput atualizar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", 
                required = true)
            RestauranteModelInput restauranteInput);
    
    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problema.class)
    })
    public ResponseEntity<Void> ativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problema.class)
    })
    public ResponseEntity<Void> inativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    public void ativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);
    
    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    public void desativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problema.class)
    })
    public ResponseEntity<Void> abreRestaurante(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problema.class)
    })
    public ResponseEntity<Void> fechaRestaurante(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
}
