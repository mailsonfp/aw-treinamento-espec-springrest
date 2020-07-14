package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.input.ProdutoFotoModelInput;
import com.algaworks.algafood.api.assembler.output.ProdutoFotoModelOutputAssembler;
import com.algaworks.algafood.api.model.output.ProdutoFotoModelOutput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.ProdutoFoto;
import com.algaworks.algafood.domain.service.CadastroProdutoFotoService;
import com.algaworks.algafood.domain.service.CadastroProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	@Autowired
	CadastroProdutoFotoService cadastroProdutoFotoService;
	
	@Autowired
	CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	ProdutoFotoModelOutputAssembler produtoFotoOut;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ProdutoFotoModelOutput atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid ProdutoFotoModelInput fotoProdutoInput) throws IOException {
		
		Produto produto = cadastroProdutoService.buscarThrow(restauranteId, produtoId);
		
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		ProdutoFoto foto = new ProdutoFoto(
				produto,							
				arquivo.getOriginalFilename(),
				fotoProdutoInput.getDescricao(),
				arquivo.getContentType(),
				arquivo.getSize());
		
		ProdutoFoto fotoSalva = cadastroProdutoFotoService.salvar(foto, arquivo.getInputStream());
		
		return produtoFotoOut.toModel(fotoSalva);
	}
	
	@PutMapping(path = "/uploadComum", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void uploadFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid ProdutoFotoModelInput fotoProdutoInput) {
		
		var nomeArquivo = UUID.randomUUID().toString()+"_"+fotoProdutoInput.getArquivo().getOriginalFilename();
		
		var arquivoFoto = Path.of("E:\\Imagens\\algaworks", nomeArquivo);
		
		try {
			fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
