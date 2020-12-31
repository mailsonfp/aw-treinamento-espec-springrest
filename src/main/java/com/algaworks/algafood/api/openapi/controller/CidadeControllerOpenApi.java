package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problema;
import com.algaworks.algafood.api.model.input.CidadeModelInput;
import com.algaworks.algafood.api.model.output.CidadeModelOutput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags="Cidades")
public interface CidadeControllerOpenApi {
	
	@ApiOperation("Lista todas as cidades")
	public CollectionModel<CidadeModelOutput> listar();
	
	@ApiOperation("Busca uma cidade por Id")
	@ApiResponses({		
		@ApiResponse(code=400, message = "Id inválido", response = Problema.class),
		@ApiResponse(code=404, message = "Não foi possível localizar uma cidade com o Id informado", response = Problema.class)
	})
	public CidadeModelOutput buscar(@ApiParam(value = "ID de um cidade", example = "1", required = true) Long cidadeId);
	
	@ApiOperation("Cadastra uma nova cidade")
	@ApiResponses({
		@ApiResponse(code=201, message = "Cidade cadastrada com sucesso")
	})
	public CidadeModelOutput adicionar(@ApiParam(name="corpo", value = "representação de um nova cidade", required = true) CidadeModelInput cidadeInput);
	
	@ApiOperation("Atualiza uma cidade por Id")
	@ApiResponses({		
		@ApiResponse(code=200, message = "Cidade atualizada com sucesso", response = Problema.class),
		@ApiResponse(code=404, message = "Não foi possível localizar uma cidade com o Id informado", response = Problema.class)
	})
    public CidadeModelOutput atualizar(
    		@ApiParam(value = "ID de um cidade", example = "1", required = true) Long cidadeId,
    		@ApiParam(name="corpo", value = "representação de uma cidade com os dados para serem atualizados", required = true) CidadeModelInput cidadeInput);
	
	@ApiOperation("Exclui uma cidade por Id")
	@ApiResponses({		
		@ApiResponse(code=204, message = "Cidade excluída com sucesso", response = Problema.class),
		@ApiResponse(code=404, message = "Não foi possível localizar uma cidade com o Id informado", response = Problema.class)
	})
	public void remover(@ApiParam(value = "ID de um cidade", example = "1", required = true) Long cidadeId);
}
