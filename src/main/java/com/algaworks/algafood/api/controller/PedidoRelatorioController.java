package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.openapi.controller.PedidoRelatorioControllerOpenApi;
import com.algaworks.algafood.domain.filter.VendaDiariaModelFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping(path = "/pedidos/relatorios")
public class PedidoRelatorioController implements PedidoRelatorioControllerOpenApi {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaModelFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		return pedidoService.consultarVendasDiarias(filtro, timeOffset);
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaModelFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		byte[] bytesPdf = vendaReportService.emitirRelatorioVendasDiarias(filtro, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio-vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
}
