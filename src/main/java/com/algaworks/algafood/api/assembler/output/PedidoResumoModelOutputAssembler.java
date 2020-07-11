package com.algaworks.algafood.api.assembler.output;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.output.PedidoResumoModelOutput;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelOutputAssembler {
	
	@Autowired
    private ModelMapper modelMapper;
    
    public PedidoResumoModelOutput toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoModelOutput.class);
    }
    
    public List<PedidoResumoModelOutput> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
}
