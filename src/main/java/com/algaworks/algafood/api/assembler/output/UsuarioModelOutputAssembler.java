package com.algaworks.algafood.api.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.output.UsuarioModelOutput;
import com.algaworks.algafood.api.util.AlgaLinks;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioModelOutputAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModelOutput> {
	
	@Autowired
    private ModelMapper modelMapper;
    
	@Autowired
	private AlgaLinks algaLinks;
	
	public UsuarioModelOutputAssembler() {
        super(UsuarioController.class, UsuarioModelOutput.class);
    }
	
	@Override
    public UsuarioModelOutput toModel(Usuario usuario) {
        UsuarioModelOutput usuarioModel = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModel);
        
        usuarioModel.add(algaLinks.linkToUsuarios("usuarios"));
        
        usuarioModel.add(algaLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        
        return usuarioModel;
    }
    
	@Override
    public CollectionModel<UsuarioModelOutput> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel());
    }
}
