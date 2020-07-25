package com.algaworks.algafood.api.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.output.PedidoModelOutput;
import com.algaworks.algafood.api.util.AlgaLinks;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;

@Component
public class PedidoModelOutputAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModelOutput> {
	
	@Autowired
    private ModelMapper modelMapper;
    
	@Autowired
	private AlgaLinks linksConstructor;
	
	public PedidoModelOutputAssembler() {
        super(PedidoController.class, PedidoModelOutput.class);
    }
		
	@Override
    public PedidoModelOutput toModel(Pedido pedido) {
        PedidoModelOutput pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        pedidoModel.add(linksConstructor.linkToPedidos("pedidos"));
        
        if(pedido.getStatus()==StatusPedido.CRIADO) {
        	pedidoModel.add(linksConstructor.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
        	
        	pedidoModel.add(linksConstructor.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
        }else if (pedido.getStatus()==StatusPedido.CONFIRMADO){
        	pedidoModel.add(linksConstructor.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
        }            
        
        pedidoModel.getRestaurante().add(
        		linksConstructor.linkToRestaurante(pedido.getRestaurante().getId()));
        
        pedidoModel.getCliente().add(
        		linksConstructor.linkToUsuario(pedido.getCliente().getId()));
        
        pedidoModel.getFormaPagamento().add(
        		linksConstructor.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        
        pedidoModel.getEnderecoEntrega().getCidade().add(
        		linksConstructor.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        
        pedidoModel.getItens().forEach(item -> {
            item.add(linksConstructor.linkToProduto(
                    pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });
        
        return pedidoModel;
    }
    
    @Override
    public CollectionModel<PedidoModelOutput> toCollectionModel(Iterable<? extends Pedido> entities) {
    	return super.toCollectionModel(entities);
    }
}
