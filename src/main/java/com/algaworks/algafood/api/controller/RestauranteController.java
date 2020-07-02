package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
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
	public Restaurante buscar(@PathVariable Long restauranteId){
		return cadastroRestauranteService.buscarThrow(restauranteId);
	}
	
	@PostMapping
	public Restaurante adicionar(@RequestBody Restaurante restaurante){
		try {
			return cadastroRestauranteService.salvar(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}		
	}
	
	@PutMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.CREATED)
    public Restaurante atualizar(@PathVariable Long restauranteId,
        @RequestBody Restaurante restaurante) {		
		Restaurante restauranteAtual = cadastroRestauranteService.buscarThrow(restauranteId);
		
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
		try {
			return cadastroRestauranteService.salvar(restauranteAtual);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}					
	}
	
	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarPorCampos(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos){		
		Restaurante restauranteAtual = cadastroRestauranteService.buscarThrow(restauranteId);
		
		merge(campos, restauranteAtual);
		
		return atualizar(restauranteId, restauranteAtual);
	}
	
	@DeleteMapping("/{restauranteId}")
	public void remover(@PathVariable Long restauranteId){
		cadastroRestauranteService.remover(restauranteId);		
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
