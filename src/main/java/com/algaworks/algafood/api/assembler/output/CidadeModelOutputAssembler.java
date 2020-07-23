package com.algaworks.algafood.api.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.model.output.CidadeModelOutput;
import com.algaworks.algafood.api.util.AlgaLinks;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelOutputAssembler extends  RepresentationModelAssemblerSupport<Cidade, CidadeModelOutput>{
	
	@Autowired
    private ModelMapper modelMapper;
    	
	@Autowired
	private AlgaLinks algaLinks;
	
	public CidadeModelOutputAssembler() {
		super(CidadeController.class, CidadeModelOutput.class);
	}
	
	@Override
    public CidadeModelOutput toModel(Cidade cidade) {
		
		/* Cria o objeto cidade model com o link "_self" habilitado
		 * CidadeModelOutput cidadeModel = createModelWithId(cidade.getId(), cidade);
		 * modelMapper.map(cidade, cidadeModel); -- para mapear de objeto para objeto e não para um instância criando o novo objeto
		 */
		
        CidadeModelOutput cidadeModel =  modelMapper.map(cidade, CidadeModelOutput.class);
        
        cidadeModel.add(algaLinks.linkToCidades("cidades"));
        
        cidadeModel.getEstado().add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));
        
        return cidadeModel;
    }
   
	@Override
	public CollectionModel<CidadeModelOutput> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
	}
}
