package com.algaworks.algafood.api.v1.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.CidadeModelInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeModelInputAssembler {
	
	 @Autowired
	 private ModelMapper modelMapper;
    
	 public Cidade toDomainObject(CidadeModelInput cidadeInput) {
		 return modelMapper.map(cidadeInput, Cidade.class);
	 }
    
	 public void copyToDomainObject(CidadeModelInput cidadeInput, Cidade cidade) {
		 cidade.setEstado(new Estado());
        
		 modelMapper.map(cidadeInput, cidade);
	 }
}
