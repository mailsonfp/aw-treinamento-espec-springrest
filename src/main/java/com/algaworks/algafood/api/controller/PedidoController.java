package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.input.PedidoModelInputAssembler;
import com.algaworks.algafood.api.assembler.output.PedidoModelOutputAssembler;
import com.algaworks.algafood.api.assembler.output.PedidoResumoModelOutputAssembler;
import com.algaworks.algafood.api.model.input.PedidoModelInput;
import com.algaworks.algafood.api.model.output.PedidoModelOutput;
import com.algaworks.algafood.api.model.output.PedidoResumoModelOutput;
import com.algaworks.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.annotations.CheckSecurityPedido;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoModelFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.domain.service.PedidoService;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private EmissaoPedidoService emissaoPedido;
    
    @Autowired
    private PedidoModelOutputAssembler pedidoModelOut;
    
    @Autowired
    private PedidoResumoModelOutputAssembler pedidoResumoModelOut;
    
    @Autowired
    private PedidoModelInputAssembler pedidoModelIn;
    
    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
    
    @Autowired
    private AlgaSecurity security;
    
	/*
	 * @GetMapping public MappingJacksonValue listar(@RequestParam(required = false)
	 * String campos) { List<Pedido> pedidos = pedidoRepository.findAll();
	 * List<PedidoResumoModelOutput> pedidosModel =
	 * pedidoResumoModelOut.toCollectionModel(pedidos);
	 * 
	 * MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
	 * 
	 * SimpleFilterProvider filterProvider = new SimpleFilterProvider();
	 * filterProvider.addFilter("pedidoFilter",
	 * SimpleBeanPropertyFilter.serializeAll());
	 * 
	 * if (StringUtils.isNotBlank(campos)) {
	 * filterProvider.addFilter("pedidoFilter",
	 * SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(","))); }
	 * 
	 * pedidosWrapper.setFilters(filterProvider);
	 * 
	 * return pedidosWrapper; }
	 */
    
    @CheckSecurityPedido.PermitePesquisar
    @GetMapping
    public CollectionModel<PedidoResumoModelOutput> listar() {
        List<Pedido> todosPedidos = pedidoService.listar();
        
        return pedidoResumoModelOut.toCollectionModel(todosPedidos);
    }
    
    @CheckSecurityPedido.PermitePesquisar
    @GetMapping("pesquisa")
    public PagedModel<PedidoResumoModelOutput> listarUsandoFiltros(PedidoModelFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
        Pageable pageableTraduzido = traduzirPageable(pageable);
        
        Page<Pedido> pedidosPage = pedidoService.pesquisar(filtro, pageableTraduzido);
        pedidosPage = new PageWrapper<>(pedidosPage, pageable);
        
        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelOut);
    }

    @CheckSecurityPedido.PermiteBuscar
    @GetMapping("/{pedidoCodigo}")
    public PedidoModelOutput buscar(@PathVariable String pedidoCodigo) {
        Pedido pedido = emissaoPedido.buscarThrow(pedidoCodigo);
        
        return pedidoModelOut.toModel(pedido);
    }    
    
    @CheckSecurityPedido.PermiteCriar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModelOutput adicionar(@Valid @RequestBody PedidoModelInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoModelIn.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(security.getUsuarioId());

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoModelOut.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
    
    public Pageable traduzirPageable(Pageable apiPageable) {
    	var mapeamento = ImmutableMap.of(
    			"codigo", "codigo",
    			"restaurante.nome", "restaurante.nome",
    			"nomeCliente", "cliente.nome",
    			"valorTotal","valorTotal");
    	
    	return PageableTranslator.translate(apiPageable, mapeamento);
    }
} 