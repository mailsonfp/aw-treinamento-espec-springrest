package com.algaworks.algafood.api.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.output.PedidoResumoModelOutput;
import com.algaworks.algafood.api.util.AlgaLinks;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelOutputAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModelOutput> {
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoResumoModelOutputAssembler() {
        super(PedidoController.class, PedidoResumoModelOutput.class);
    }
    
	@Override
    public PedidoResumoModelOutput toModel(Pedido pedido) {
        PedidoResumoModelOutput pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
        
        pedidoModel.getRestaurante().add(
                algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
        
        return pedidoModel;
    }
    
    @Override
    public CollectionModel<PedidoResumoModelOutput> toCollectionModel(Iterable<? extends Pedido> entities) {
    	return super.toCollectionModel(entities);
    }
}
