package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.output.EnderecoModelOutput;
import com.algaworks.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModelOutput.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				origem -> origem.getCidade().getEstado().getNome(),
				(destino, value) -> destino.getCidade().setEstado(value));
		
		return modelMapper;
	}
}
