package com.algaworks.algafood.api.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.ProdutoFotoModelOutput;
import com.algaworks.algafood.domain.model.ProdutoFoto;

@Component
public class ProdutoFotoModelOutputAssembler {
	
	@Autowired
    private ModelMapper modelMapper;
    
    public ProdutoFotoModelOutput toModel(ProdutoFoto produto) {
        return modelMapper.map(produto, ProdutoFotoModelOutput.class);
    }
}
