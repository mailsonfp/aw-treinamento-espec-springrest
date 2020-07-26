package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.v1.model.input.ItemPedidoModelInput;
import com.algaworks.algafood.api.v1.model.output.EnderecoModelOutput;
import com.algaworks.algafood.api.v2.model.input.CidadeModelInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModelOutput.class);
		
		modelMapper.createTypeMap(ItemPedidoModelInput.class, ItemPedido.class)
	    .addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		modelMapper.createTypeMap(CidadeModelInputV2.class, Cidade.class)
	    .addMappings(mapper -> mapper.skip(Cidade::setId));
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				origem -> origem.getCidade().getEstado().getNome(),
				(destino, value) -> destino.getCidade().setEstado(value));
		
		return modelMapper;
	}
}
