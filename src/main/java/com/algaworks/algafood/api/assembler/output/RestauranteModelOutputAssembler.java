package com.algaworks.algafood.api.assembler.output;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteModelInput;
import com.algaworks.algafood.api.model.output.RestauranteModelOutput;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelOutputAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteModelOutput toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteModelOutput.class);
	}
	
	public RestauranteModelInput toModelInput(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteModelInput.class);
	}
	
	public List<RestauranteModelOutput> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
}
