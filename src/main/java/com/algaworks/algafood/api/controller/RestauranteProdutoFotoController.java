package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.input.ProdutoFotoModelInput;
import com.algaworks.algafood.api.assembler.output.ProdutoFotoModelOutputAssembler;
import com.algaworks.algafood.api.model.output.ProdutoFotoModelOutput;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.core.security.annotations.CheckSecurityRestaurante;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.ProdutoFoto;
import com.algaworks.algafood.domain.service.CadastroProdutoFotoService;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.ProdutoFotoStorageService;
import com.algaworks.algafood.domain.service.ProdutoFotoStorageService.FotoRecuperada;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {
	
	@Autowired
	CadastroProdutoFotoService cadastroProdutoFotoService;
	
	@Autowired
	CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	ProdutoFotoModelOutputAssembler produtoFotoOut;
	
	@Autowired
	ProdutoFotoStorageService produtoFotoStorage;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ProdutoFotoModelOutput buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return produtoFotoOut.toModel(cadastroProdutoFotoService.buscarThrow(restauranteId, produtoId));
	}
	
	@CheckSecurityRestaurante.PermiteGerenciarFuncionamento
	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader)
			throws HttpMediaTypeNotAcceptableException {
		try {
			ProdutoFoto produtoFoto = cadastroProdutoFotoService.buscarThrow(restauranteId, produtoId);
			FotoRecuperada fotoRecuperada = produtoFotoStorage.recuperar(produtoFoto.getNomeArquivo());
		
			MediaType mediaTypeFoto = MediaType.parseMediaType(produtoFoto.getContentType());
			List<MediaType> mediasAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificaTipoFoto(mediaTypeFoto, mediasAceitas);	

			if(fotoRecuperada.temUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			}else {
				return ResponseEntity.ok()
						.contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}	

	@CheckSecurityRestaurante.PermiteGerenciarFuncionamento
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ProdutoFotoModelOutput atualizarFoto(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId,
			@Valid ProdutoFotoModelInput fotoProdutoInput) throws IOException {
		
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
	
	@CheckSecurityRestaurante.PermiteGerenciarFuncionamento
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		cadastroProdutoFotoService.remover(restauranteId, produtoId);
	}
	
	private void verificaTipoFoto(MediaType mediaTypeFoto, List<MediaType> mediasAceitas) throws HttpMediaTypeNotAcceptableException {
		boolean ok = mediasAceitas.stream().anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		if(!ok) {
			throw new HttpMediaTypeNotAcceptableException(mediasAceitas);
		}
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
