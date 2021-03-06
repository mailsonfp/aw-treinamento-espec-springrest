package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class VendaDiariaModelFilter {
	
	private Long restauranteId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoInicio;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;

	public Long getRestauranteId() {
		return restauranteId;
	}
	public void setRestauranteId(Long restauranteId) {
		this.restauranteId = restauranteId;
	}
	public OffsetDateTime getDataCriacaoInicio() {
		return dataCriacaoInicio;
	}
	public void setDataCriacaoInicio(OffsetDateTime dataCriacaoInicio) {
		this.dataCriacaoInicio = dataCriacaoInicio;
	}
	public OffsetDateTime getDataCriacaoFim() {
		return dataCriacaoFim;
	}
	public void setDataCriacaoFim(OffsetDateTime dataCriacaoFim) {
		this.dataCriacaoFim = dataCriacaoFim;
	}
}
