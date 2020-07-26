package com.algaworks.algafood.api.v1.model.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;

public class PedidoModelOutput extends RepresentationModel<PedidoModelOutput> {
	
	@ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
	private String codigo;
	

	@ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;
	
	@ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "308.90")
    private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "CRIADO")
    private String status;
	
	@ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataCriacao;
	
	@ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataConfirmacao;
	
	@ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataEntrega;
	
	@ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataCancelamento;
	
    private RestauranteResumoModelOutput restaurante;
    private UsuarioModelOutput cliente;
    private FormaPagamentoModelOutput formaPagamento;
    private EnderecoModelOutput enderecoEntrega;
    private List<ItemPedidoModelOutput> itens;
	
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
	public OffsetDateTime getDataConfirmacao() {
		return dataConfirmacao;
	}
	public void setDataConfirmacao(OffsetDateTime dataConfirmacao) {
		this.dataConfirmacao = dataConfirmacao;
	}
	public OffsetDateTime getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(OffsetDateTime dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public OffsetDateTime getDataCancelamento() {
		return dataCancelamento;
	}
	public void setDataCancelamento(OffsetDateTime dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
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
	public FormaPagamentoModelOutput getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(FormaPagamentoModelOutput formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public EnderecoModelOutput getEnderecoEntrega() {
		return enderecoEntrega;
	}
	public void setEnderecoEntrega(EnderecoModelOutput enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}
	public List<ItemPedidoModelOutput> getItens() {
		return itens;
	}
	public void setItens(List<ItemPedidoModelOutput> itens) {
		this.itens = itens;
	}
    
}
