package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.output.PedidoModelInputAssembler;
import com.algaworks.algafood.api.assembler.output.PedidoModelOutputAssembler;
import com.algaworks.algafood.api.assembler.output.PedidoResumoModelOutputAssembler;
import com.algaworks.algafood.api.model.input.PedidoModelInput;
import com.algaworks.algafood.api.model.output.PedidoModelOutput;
import com.algaworks.algafood.api.model.output.PedidoResumoModelOutput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private EmissaoPedidoService emissaoPedido;
    
    @Autowired
    private PedidoModelOutputAssembler pedidoModelOut;
    
    @Autowired
    private PedidoResumoModelOutputAssembler pedidoResumoModelOut;
    
    @Autowired
    private PedidoModelInputAssembler pedidoModelIn;
    
    @GetMapping
    public List<PedidoResumoModelOutput> listar() {
        List<Pedido> todosPedidos = pedidoRepository.findAll();
        
        return pedidoResumoModelOut.toCollectionModel(todosPedidos);
    }
    
    @GetMapping("/{pedidoCodigo}")
    public PedidoModelOutput buscar(@PathVariable String pedidoCodigo) {
        Pedido pedido = emissaoPedido.buscarThrow(pedidoCodigo);
        
        return pedidoModelOut.toModel(pedido);
    }    
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModelOutput adicionar(@Valid @RequestBody PedidoModelInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoModelIn.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoModelOut.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
} 