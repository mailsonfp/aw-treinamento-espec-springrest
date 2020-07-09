package com.algaworks.algafood.api.assembler.output;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.UsuarioModelOutput;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioModelOutputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public UsuarioModelOutput toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModelOutput.class);
    }
    
    public List<UsuarioModelOutput> toCollectionModel(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toModel(usuario))
                .collect(Collectors.toList());
    }
}
