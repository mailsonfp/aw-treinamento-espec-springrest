package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
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
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
		Optional<Restaurante> restaurante = cadastroRestauranteService.buscar(restauranteId);
			
		if(!restaurante.isPresent()) {
			return ResponseEntity.notFound().build();
		}	
		
		return ResponseEntity.ok(restaurante.get());
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(cadastroRestauranteService.salvar(restaurante));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	@PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
        @RequestBody Restaurante restaurante) {		
		try {
			Restaurante restauranteAtual = cadastroRestauranteService.buscar(restauranteId).orElse(null);
			
			if (restauranteAtual==null) {
				return ResponseEntity.notFound().build();				
			}
			
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
			
			Restaurante restauranteSalvo = cadastroRestauranteService.salvar(restauranteAtual);
			return ResponseEntity.ok(restauranteSalvo);	
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}		
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarPorCampos(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos){
		
		Restaurante restauranteAtual = cadastroRestauranteService.buscar(restauranteId).orElse(null);
		
		if (restauranteAtual == null) {
			ResponseEntity.notFound().build();
		}
				
		merge(campos, restauranteAtual);
		return atualizar(restauranteId, restauranteAtual);
	}
	
	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId){
		try {
			cadastroRestauranteService.remover(restauranteId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}		
	}
	
	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		
		ObjectMapper objectMapper = new ObjectMapper();	
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		
		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {							
			Field campo = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			campo.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(campo, restauranteOrigem);
			
			ReflectionUtils.setField(campo, restauranteDestino, novoValor);	
		});
	}
}
