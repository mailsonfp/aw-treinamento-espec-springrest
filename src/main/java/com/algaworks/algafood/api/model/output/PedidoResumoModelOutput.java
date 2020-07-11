package com.algaworks.algafood.api.model.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class PedidoResumoModelOutput {
	
	private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumoModelOutput restaurante;
    private UsuarioModelOutput cliente;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}
	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OffsetDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(OffsetDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public RestauranteResumoModelOutput getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(RestauranteResumoModelOutput restaurante) {
		this.restaurante = restaurante;
	}
	public UsuarioModelOutput getCliente() {
		return cliente;
	}
	public void setCliente(UsuarioModelOutput cliente) {
		this.cliente = cliente;
	}    
}
