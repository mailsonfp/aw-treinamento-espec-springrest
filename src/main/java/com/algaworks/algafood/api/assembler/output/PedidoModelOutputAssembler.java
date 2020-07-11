package com.algaworks.algafood.api.assembler.output;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.PedidoModelOutput;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoModelOutputAssembler {
	
	@Autowired
    private ModelMapper modelMapper;
    
    public PedidoModelOutput toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModelOutput.class);
    }
    
    public List<PedidoModelOutput> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
}
