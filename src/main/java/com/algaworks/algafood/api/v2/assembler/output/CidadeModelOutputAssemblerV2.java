package com.algaworks.algafood.api.v2.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood.api.v2.model.output.CidadeModelOutputV2;
import com.algaworks.algafood.api.v2.util.AlgaLinksV2;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelOutputAssemblerV2 extends  RepresentationModelAssemblerSupport<Cidade, CidadeModelOutputV2>{
	
	@Autowired
    private ModelMapper modelMapper;
    	
	@Autowired
	private AlgaLinksV2 algaLinks;
	
	public CidadeModelOutputAssemblerV2() {
		super(CidadeControllerV2.class, CidadeModelOutputV2.class);
	}
	
	@Override
    public CidadeModelOutputV2 toModel(Cidade cidade) {
		CidadeModelOutputV2 cidadeModel = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeModel);		
        
        cidadeModel.add(algaLinks.linkToCidades("cidades"));
        
        return cidadeModel;
    }
   
	@Override
	public CollectionModel<CidadeModelOutputV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToCidades());
	}
}
