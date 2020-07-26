package com.algaworks.algafood.api.v1.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.GrupoModelInput;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoModelInputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public Grupo toDomainObject(GrupoModelInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }
    
    public void copyToDomainObject(GrupoModelInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}
