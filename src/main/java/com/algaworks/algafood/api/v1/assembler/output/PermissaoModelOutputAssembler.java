package com.algaworks.algafood.api.v1.assembler.output;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.output.PermissaoModelOutput;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoModelOutputAssembler {
	
	@Autowired
    private ModelMapper modelMapper;
    
    public PermissaoModelOutput toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModelOutput.class);
    }
    
    public List<PermissaoModelOutput> toCollectionModel(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(permissao -> toModel(permissao))
                .collect(Collectors.toList());
    }
}
