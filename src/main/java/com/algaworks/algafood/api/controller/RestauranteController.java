package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoPatchException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private SmartValidator validator;
	
	@GetMapping
	public List<Restaurante> listar(){
		return cadastroRestauranteService.listar();
	}
	
	@GetMapping("/por-taxa-frete")
	public List<Restaurante> listarPorTaxaFrete(@RequestParam BigDecimal taxaInicial, @RequestParam BigDecimal taxaFinal){
		return cadastroRestauranteService.listarPorTaxaEntrega(taxaInicial, taxaFinal);
	}
	
	
	@GetMapping("/por-nome-taxa-frete")
	public List<Restaurante> listarPorNomeTaxaEntrega(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		return cadastroRestauranteService.listarPorNomeTaxaEntrega(nome, taxaInicial, taxaFinal);
	}
	 
	
	@GetMapping("/por-nome")
	public List<Restaurante> listarPorNome(@RequestParam String nome, @RequestParam Long cozinhaId){
		return cadastroRestauranteService.listarPorNomeId(nome, cozinhaId);
	}
	
	@GetMapping("/com-frete-gratis")
	public List<Restaurante> listarlistarComFreteGratisNome(String nome){
		return cadastroRestauranteService.listarComFreteGratisNome(nome);
	}
	
	@GetMapping("/primeiro")
	public Optional<Restaurante> buscarPrimeiro(){
		return cadastroRestauranteService.buscarPrimeiro();
	}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId){
		return cadastroRestauranteService.buscarThrow(restauranteId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante){
		try {
			return cadastroRestauranteService.salvar(restaurante);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}		
	}
	
	@PutMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.CREATED)
    public Restaurante atualizar(@PathVariable Long restauranteId,
        @RequestBody @Valid Restaurante restaurante) {		
		Restaurante restauranteAtual = cadastroRestauranteService.buscarThrow(restauranteId);
		
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
		try {
			return cadastroRestauranteService.salvar(restauranteAtual);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}					
	}
	
	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarPorCampos(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request){		
		Restaurante restauranteAtual = cadastroRestauranteService.buscarThrow(restauranteId);
		
		merge(campos, restauranteAtual, request);
		
		validate(restauranteAtual, "restaurante");
		
		return atualizar(restauranteId, restauranteAtual);
	}
	
	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		
		validator.validate(restaurante, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoPatchException(bindingResult);
		}
	}

	@DeleteMapping("/{restauranteId}")
	public void remover(@PathVariable Long restauranteId){
		cadastroRestauranteService.remover(restauranteId);		
	}
	
	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
			
			camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {							
				Field campo = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				campo.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(campo, restauranteOrigem);
				
				ReflectionUtils.setField(campo, restauranteDestino, novoValor);	
			});
		} catch (Exception e) {
			throw new HttpMessageNotReadableException(e.getMessage(), ExceptionUtils.getRootCause(e), serverHttpRequest);
		}
		
	}
}
