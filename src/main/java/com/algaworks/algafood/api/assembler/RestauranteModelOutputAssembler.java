package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CozinhaIdModelInput;
import com.algaworks.algafood.api.model.input.RestauranteModelInput;
import com.algaworks.algafood.api.model.output.CozinhaModelOutput;
import com.algaworks.algafood.api.model.output.RestauranteModelOutput;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelOutputAssembler {
	
	public RestauranteModelOutput toModel(Restaurante restaurante) {
		CozinhaModelOutput cozinhaModel = new CozinhaModelOutput();
		cozinhaModel.setId(restaurante.getCozinha().getId());
		cozinhaModel.setNome(restaurante.getCozinha().getNome());
		
		RestauranteModelOutput restauranteModel = new RestauranteModelOutput();
		restauranteModel.setId(restaurante.getId());
		restauranteModel.setNome(restaurante.getNome());
		restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteModel.setCozinha(cozinhaModel);
		return restauranteModel;
	}
	
	public RestauranteModelInput toModelInput(Restaurante restaurante) {
		CozinhaIdModelInput cozinhaId = new CozinhaIdModelInput();
		cozinhaId.setId(restaurante.getCozinha().getId());
		
		RestauranteModelInput restauranteModelInput = new RestauranteModelInput();
		restauranteModelInput.setNome(restaurante.getNome());
		restauranteModelInput.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteModelInput.setCozinha(cozinhaId);
		return restauranteModelInput;
	}
	
	public List<RestauranteModelOutput> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
}
