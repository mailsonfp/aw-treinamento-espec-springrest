package com.algaworks.algafood.api.v2.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.model.input.CidadeModelInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeModelInputAssemblerV2 {
	
	 @Autowired
	 private ModelMapper modelMapper;
    
	 public Cidade toDomainObject(CidadeModelInputV2 cidadeInput) {
		 return modelMapper.map(cidadeInput, Cidade.class);
	 }
    
	 public void copyToDomainObject(CidadeModelInputV2 cidadeInput, Cidade cidade) {
		 cidade.setEstado(new Estado());
        
		 modelMapper.map(cidadeInput, cidade);
	 }
}
