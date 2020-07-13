package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.filter.VendaDiariaModelFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

public interface PedidoRepositoryCustom {
	
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaModelFilter filtro, String timeOffset);
}
