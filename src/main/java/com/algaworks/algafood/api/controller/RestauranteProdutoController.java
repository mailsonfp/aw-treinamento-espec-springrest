package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.input.ProdutoModelInputAssembler;
import com.algaworks.algafood.api.assembler.output.ProdutoModelOutputAssembler;
import com.algaworks.algafood.api.model.input.ProdutoModelInput;
import com.algaworks.algafood.api.model.output.ProdutoModelOutput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	@Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private CadastroProdutoService cadastroProduto;
    
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;
    
    @Autowired
    private ProdutoModelOutputAssembler produtoModelOut;
    
    @Autowired
    private ProdutoModelInputAssembler produtoModelIn;
    
    @GetMapping
    public List<ProdutoModelOutput> listar(@PathVariable Long restauranteId, @RequestParam(required=false) boolean incluirInativos) {
        Restaurante restaurante = cadastroRestaurante.buscarThrow(restauranteId);
        
        List<Produto> todosProdutos = null;
        if(incluirInativos) {
        	todosProdutos = produtoRepository.findByRestaurante(restaurante);
        }else {
        	todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
        }
        
        return produtoModelOut.toCollectionModel(todosProdutos);
    }
    
    @GetMapping("/{produtoId}")
    public ProdutoModelOutput buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        
        return produtoModelOut.toModel(produto);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModelOutput adicionar(@PathVariable Long restauranteId,
            @RequestBody @Valid ProdutoModelInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscarThrow(restauranteId);
        
        Produto produto = produtoModelIn.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        
        produto = cadastroProduto.salvar(produto);
        
        return produtoModelOut.toModel(produto);
    }
    
    @PutMapping("/{produtoId}")
    public ProdutoModelOutput atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @RequestBody @Valid ProdutoModelInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        
        produtoModelIn.copyToDomainObject(produtoInput, produtoAtual);
        
        produtoAtual = cadastroProduto.salvar(produtoAtual);
        
        return produtoModelOut.toModel(produtoAtual);
    }
}
