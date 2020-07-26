package com.algaworks.algafood.api.v1.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.ProdutoModelInput;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoModelInputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public Produto toDomainObject(ProdutoModelInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }
    
    public void copyToDomainObject(ProdutoModelInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }
}
