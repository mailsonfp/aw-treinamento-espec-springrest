package com.algaworks.algafood.api.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.input.RestauranteModelInput;
import com.algaworks.algafood.api.model.output.RestauranteModelOutput;
import com.algaworks.algafood.api.util.AlgaLinks;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelOutputAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModelOutput> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private AlgaLinks algaLinks;
	
	public RestauranteModelOutputAssembler() {
        super(RestauranteController.class, RestauranteModelOutput.class);
    }
	
	@Override
    public RestauranteModelOutput toModel(Restaurante restaurante) {
        RestauranteModelOutput restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        
        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        
        restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
        
        if(restaurante.getEndereco()!=null){        	
        	restauranteModel.getEndereco().getCidade().add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
        }
        
        restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(),"formas-pagamento"));
        
        restauranteModel.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(),"responsaveis"));
        
        if (restaurante.ativacaoPermitida()) {
        	restauranteModel.add(
        			algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
        	restauranteModel.add(
        			algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
        	restauranteModel.add(
        			algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
        	restauranteModel.add(
        			algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }
        
        return restauranteModel;
    }
	
	public RestauranteModelInput toModelInput(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteModelInput.class);
	}
	
	@Override
    public CollectionModel<RestauranteModelOutput> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }
}
