package com.algaworks.algafood.api.v2.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.model.input.CozinhaModelInputV2;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelInputAssemblerV2 {
	@Autowired
    private ModelMapper modelMapper;
    
    public Cozinha toDomainObject(CozinhaModelInputV2 cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }
    
    public void copyToDomainObject(CozinhaModelInputV2 cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
}
