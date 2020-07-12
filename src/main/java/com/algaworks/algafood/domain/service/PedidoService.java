package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.filter.PedidoModelFilter;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;

@Service
public class PedidoService {
	
	@Autowired
    private PedidoRepository pedidoRepository;
	
	public List<Pedido> listar(){
		return pedidoRepository.findAll();
	}
	
	public Page<Pedido> pesquisar(PedidoModelFilter filtro, Pageable pageable){
		return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
	}
	
	public Optional<Pedido> buscarPorCodigo(String pedidoCodigo){
		return pedidoRepository.findByCodigo(pedidoCodigo);
	}
	
	public Pedido salvar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
}
