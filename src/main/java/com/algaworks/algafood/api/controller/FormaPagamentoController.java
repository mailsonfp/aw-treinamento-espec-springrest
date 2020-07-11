package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.input.FormaPagamentoModelInputAssembler;
import com.algaworks.algafood.api.assembler.output.FormaPagamentoModelOutputAssembler;
import com.algaworks.algafood.api.model.input.FormaPagamentoModelInput;
import com.algaworks.algafood.api.model.output.FormaPagamentoModelOutput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	
	@Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    
    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;
    
    @Autowired
    private FormaPagamentoModelOutputAssembler formaPagamentoOut;
    
    @Autowired
    private FormaPagamentoModelInputAssembler formaPagamentoIn;
    
    @GetMapping
    public List<FormaPagamentoModelOutput> listar() {
        List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
        
        return formaPagamentoOut.toCollectionModel(todasFormasPagamentos);
    }
    
    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoModelOutput buscar(@PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarThrow(formaPagamentoId);
        
        return formaPagamentoOut.toModel(formaPagamento);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModelOutput adicionar(@RequestBody @Valid FormaPagamentoModelInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoIn.toDomainObject(formaPagamentoInput);
        
        formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);
        
        return formaPagamentoOut.toModel(formaPagamento);
    }
    
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoModelOutput atualizar(@PathVariable Long formaPagamentoId,
            @RequestBody @Valid FormaPagamentoModelInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarThrow(formaPagamentoId);
        
        formaPagamentoIn.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
        
        formaPagamentoAtual = cadastroFormaPagamento.salvar(formaPagamentoAtual);
        
        return formaPagamentoOut.toModel(formaPagamentoAtual);
    }
    
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamento.remover(formaPagamentoId);	
    }  
}
