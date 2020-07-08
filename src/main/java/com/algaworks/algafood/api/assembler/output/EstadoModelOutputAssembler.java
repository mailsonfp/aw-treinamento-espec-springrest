package com.algaworks.algafood.api.assembler.output;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.EstadoModelOutput;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoModelOutputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public EstadoModelOutput toModel(Estado estado) {
        return modelMapper.map(estado, EstadoModelOutput.class);
    }
    
    public List<EstadoModelOutput> toCollectionModel(List<Estado> estados) {
        return estados.stream()
                .map(estado -> toModel(estado))
                .collect(Collectors.toList());
    }
}
