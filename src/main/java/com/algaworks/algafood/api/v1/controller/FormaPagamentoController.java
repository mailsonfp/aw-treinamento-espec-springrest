package com.algaworks.algafood.api.v1.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.input.FormaPagamentoModelInputAssembler;
import com.algaworks.algafood.api.v1.assembler.output.FormaPagamentoModelOutputAssembler;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoModelInput;
import com.algaworks.algafood.api.v1.model.output.FormaPagamentoModelOutput;
import com.algaworks.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/v1/formas-pagamento")
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi{
	
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
    
    @GetMapping("/com-cache")
    public ResponseEntity<List<FormaPagamentoModelOutput>> listarComCash() {
    	
        List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
        
        List<FormaPagamentoModelOutput> listaFormasPagamento = formaPagamentoOut.toCollectionModel(todasFormasPagamentos);
        
        return ResponseEntity.ok()
        		.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
        		.body(listaFormasPagamento);
    }
    
    /*@GetMapping
	public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
		List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
		
		List<FormaPagamentoModel> formasPagamentosModel = formaPagamentoModelAssembler
				.toCollectionModel(todasFormasPagamentos);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formasPagamentosModel);
	}*/
    
    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoModelOutput buscar(@PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarThrow(formaPagamentoId);
        
        return formaPagamentoOut.toModel(formaPagamento);
    }
    
    @GetMapping("com-cache/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoModelOutput> buscarComCache(@PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarThrow(formaPagamentoId);
        
        return ResponseEntity.ok()
//        		.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
        		.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//        		.cacheControl(CacheControl.noCache()) ao fazer cache sempre precisa validar, sempre está em stale
//        		.cacheControl(CacheControl.noStore()) desativa o cache, não vai armazenar ninguém
//        		.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
        		.body(formaPagamentoOut.toModel(formaPagamento));
    }
    
    /**@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId,
	        ServletWebRequest request) {
	    
	    ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
	    
	    String eTag = "0";
	    
	    OffsetDateTime dataAtualizacao = formaPagamentoRepository
	            .getDataAtualizacaoById(formaPagamentoId);
	    
	    if (dataAtualizacao != null) {
	        eTag = String.valueOf(dataAtualizacao.toEpochSecond());
	    }
	    
	    if (request.checkNotModified(eTag)) {
	        return null;
	    }
	    
	    FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
	    
	    FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);
	    
	    return ResponseEntity.ok()
	            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
	            .eTag(eTag)
	            .body(formaPagamentoModel);
	}*/
    
    
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
