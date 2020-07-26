package com.algaworks.algafood.api.v1.assembler.output;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.output.GrupoModelOutput;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoModelOutputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public GrupoModelOutput toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModelOutput.class);
    }
    
    public List<GrupoModelOutput> toCollectionModel(Collection<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toModel(grupo))
                .collect(Collectors.toList());
    }
}
