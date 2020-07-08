package com.algaworks.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.EstadoModelInput;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoModelInputAssembler {
	
	@Autowired
    private ModelMapper modelMapper;
    
    public Estado toDomainObject(EstadoModelInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }
    
    public void copyToDomainObject(EstadoModelInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }
}
