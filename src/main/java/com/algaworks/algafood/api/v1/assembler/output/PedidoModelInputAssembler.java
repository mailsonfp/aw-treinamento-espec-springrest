package com.algaworks.algafood.api.v1.assembler.output;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.PedidoModelInput;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoModelInputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public Pedido toDomainObject(PedidoModelInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }
    
    public void copyToDomainObject(PedidoModelInput pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }
}
