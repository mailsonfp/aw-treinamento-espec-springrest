package com.algaworks.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteModelInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelInputAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteModelInput restauranteInput) {		
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteModelInput restauranteIn, Restaurante restaurante) {
		//correção - identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 4 to 5
		restaurante.setCozinha(new Cozinha());
		modelMapper.map(restauranteIn, restaurante);
	}
}
