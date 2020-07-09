package com.algaworks.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.FormaPagamentoModelInput;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelInputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public FormaPagamento toDomainObject(FormaPagamentoModelInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }
    
    public void copyToDomainObject(FormaPagamentoModelInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    } 
}
