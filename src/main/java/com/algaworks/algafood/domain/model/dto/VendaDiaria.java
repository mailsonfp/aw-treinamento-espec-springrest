package com.algaworks.algafood.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

public class VendaDiaria {

	private Date data;
	private Long quantidadeVendas;
	private BigDecimal totalFaturado;
	
	public VendaDiaria(Date data, Long totalVendas, BigDecimal totalFaturado) {
		super();
		this.data = data;
		this.quantidadeVendas = totalVendas;
		this.totalFaturado = totalFaturado;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Long getQuantidadeVendas() {
		return quantidadeVendas;
	}
	public void setQuantidadeVendas(Long totalVendas) {
		this.quantidadeVendas = totalVendas;
	}
	public BigDecimal getTotalFaturado() {
		return totalFaturado;
	}
	public void setTotalFaturado(BigDecimal totalFaturado) {
		this.totalFaturado = totalFaturado;
	}
	
}
