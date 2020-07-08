package com.algaworks.algafood.api.assembler.output;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.CidadeModelOutput;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelOutputAssembler {
	
	@Autowired
    private ModelMapper modelMapper;
    
    public CidadeModelOutput toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModelOutput.class);
    }
    
    public List<CidadeModelOutput> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }
}
