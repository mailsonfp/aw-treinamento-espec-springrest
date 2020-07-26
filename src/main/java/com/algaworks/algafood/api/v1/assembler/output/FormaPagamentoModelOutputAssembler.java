package com.algaworks.algafood.api.v1.assembler.output;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.output.FormaPagamentoModelOutput;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelOutputAssembler {
	@Autowired
    private ModelMapper modelMapper;
    
    public FormaPagamentoModelOutput toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModelOutput.class);
    }
    
    public List<FormaPagamentoModelOutput> toCollectionModel(Collection<FormaPagamento> formasPagamentos) {
        return formasPagamentos.stream()
                .map(formaPagamento -> toModel(formaPagamento))
                .collect(Collectors.toList());
    }
    
}
