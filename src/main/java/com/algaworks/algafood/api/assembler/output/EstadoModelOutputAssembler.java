package com.algaworks.algafood.api.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.output.EstadoModelOutput;
import com.algaworks.algafood.api.util.AlgaLinks;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoModelOutputAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModelOutput> {
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public EstadoModelOutputAssembler() {
        super(EstadoController.class, EstadoModelOutput.class);
    }
    
	@Override
    public EstadoModelOutput toModel(Estado estado) {
		
        EstadoModelOutput estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);
        
        estadoModel.add(algaLinks.linkToEstados("estados"));

        return estadoModel;
    }
    
	@Override
    public CollectionModel<EstadoModelOutput> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
            .add(WebMvcLinkBuilder.linkTo(EstadoController.class).withSelfRel());
    }
}
