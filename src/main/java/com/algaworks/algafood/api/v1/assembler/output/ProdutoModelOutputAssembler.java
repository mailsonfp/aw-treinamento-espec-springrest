package com.algaworks.algafood.api.v1.assembler.output;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.output.ProdutoModelOutput;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoModelOutputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public ProdutoModelOutput toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoModelOutput.class);
    }
    
    public List<ProdutoModelOutput> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toModel(produto))
                .collect(Collectors.toList());
    }
}
