package com.algaworks.algafood.api.v1.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.model.output.CozinhaModelOutput;
import com.algaworks.algafood.api.v1.util.AlgaLinks;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelOutputAssembler  extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelOutput>{
	@Autowired
    private ModelMapper modelMapper;
    
	@Autowired
	private AlgaLinks algaLinks;
	
	public CozinhaModelOutputAssembler() {
		super(CozinhaController.class, CozinhaModelOutput.class);
	}
	
    public CozinhaModelOutput toModel(Cozinha cozinha) {
    	CozinhaModelOutput cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
    	
    	modelMapper.map(cozinha, cozinhaModel);
    	
    	cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));
    	
    	return cozinhaModel;
    }
    
    @Override
	public CollectionModel<CozinhaModelOutput> toCollectionModel(Iterable<? extends Cozinha> entities) {
		return super.toCollectionModel(entities)
			.add(WebMvcLinkBuilder.linkTo(CozinhaController.class).withSelfRel());
	}
}
