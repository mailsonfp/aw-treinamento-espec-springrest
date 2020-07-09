package com.algaworks.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.UsuarioModelInputDados;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioModelInputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public Usuario toDomainObject(UsuarioModelInputDados usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }
    
    public void copyToDomainObject(UsuarioModelInputDados usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }  
}
