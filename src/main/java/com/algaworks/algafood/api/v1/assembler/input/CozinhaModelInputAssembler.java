package com.algaworks.algafood.api.v1.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.CozinhaModelInput;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelInputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public Cozinha toDomainObject(CozinhaModelInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }
    
    public void copyToDomainObject(CozinhaModelInput cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
}
