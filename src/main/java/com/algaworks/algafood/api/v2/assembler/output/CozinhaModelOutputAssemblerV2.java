package com.algaworks.algafood.api.v2.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.controller.CozinhaControllerV2;
import com.algaworks.algafood.api.v2.model.output.CozinhaModelOutputV2;
import com.algaworks.algafood.api.v2.util.AlgaLinksV2;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelOutputAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelOutputV2>{
	@Autowired
    private ModelMapper modelMapper;
    
	@Autowired
	private AlgaLinksV2 algaLinks;
	
	public CozinhaModelOutputAssemblerV2() {
		super(CozinhaControllerV2.class, CozinhaModelOutputV2.class);
	}
	
    public CozinhaModelOutputV2 toModel(Cozinha cozinha) {
    	CozinhaModelOutputV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
    	
    	modelMapper.map(cozinha, cozinhaModel);
    	
    	cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));
    	
    	return cozinhaModel;
    }
    
    @Override
	public CollectionModel<CozinhaModelOutputV2> toCollectionModel(Iterable<? extends Cozinha> entities) {
		return super.toCollectionModel(entities)
			.add(algaLinks.linkToCozinhas("cozinhas"));
	}
}
