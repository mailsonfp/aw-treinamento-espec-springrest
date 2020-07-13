package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaModelFilter;

public interface VendaReportService {
	byte[] emitirRelatorioVendasDiarias(VendaDiariaModelFilter filtro, String timeOffset);
}
