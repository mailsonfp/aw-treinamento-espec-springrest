package com.algaworks.algafood.api.assembler.output;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.CozinhaModelOutput;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelOutputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public CozinhaModelOutput toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaModelOutput.class);
    }
    
    public List<CozinhaModelOutput> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toModel(cozinha))
                .collect(Collectors.toList());
    }
}
