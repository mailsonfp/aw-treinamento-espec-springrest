package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.input.RestauranteModelInputAssembler;
import com.algaworks.algafood.api.assembler.output.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.assembler.output.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.assembler.output.RestauranteModelOutputAssembler;
import com.algaworks.algafood.api.model.input.RestauranteModelInput;
import com.algaworks.algafood.api.model.output.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.model.output.RestauranteBasicoModel;
import com.algaworks.algafood.api.model.output.RestauranteModelOutput;
import com.algaworks.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.exception.ValidacaoPatchException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

//@CrossOrigin(origins = "http://127.0.0.1:8000", maxAge = 10)// --- sem a definição de orgins, aceita requisição de qualquer origem
@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private SmartValidator validator;	
	
	@Autowired
	private RestauranteModelOutputAssembler restauranteModelOutAssembler;
	
	@Autowired
	private RestauranteModelInputAssembler restauranteModelInAssembler;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;
	
	
	@GetMapping
	public CollectionModel<RestauranteModelOutput> listar(){
		return restauranteModelOutAssembler.toCollectionModel(cadastroRestauranteService.listar());
	}
	
	//@JsonView(RestauranteView.Resumo.class)
	@GetMapping(params = "projecao=resumo")
	public CollectionModel<RestauranteBasicoModel> listarComResumo(){
		return restauranteBasicoModelAssembler.toCollectionModel(cadastroRestauranteService.listar());
	}
	
	//@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNome(){
		return restauranteApenasNomeModelAssembler.toCollectionModel(cadastroRestauranteService.listar());
	}
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);
//		
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);
//		
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//		
//		if ("apenas-nome".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		} else if ("completo".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(null);
//		}
//		
//		return restaurantesWrapper;
//	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteModelOutput buscar(@PathVariable Long restauranteId){
		return restauranteModelOutAssembler.toModel(cadastroRestauranteService.buscarThrow(restauranteId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModelOutput adicionar(@RequestBody @Valid RestauranteModelInput restauranteInput){
		try {
			Restaurante restaurante = restauranteModelInAssembler.toDomainObject(restauranteInput);
			return restauranteModelOutAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}		
	}
	
	@PutMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.CREATED)
    public RestauranteModelOutput atualizar(@PathVariable Long restauranteId,
        @RequestBody @Valid RestauranteModelInput restauranteInput) {		
		Restaurante restauranteAtual = cadastroRestauranteService.buscarThrow(restauranteId);
		
		restauranteModelInAssembler.copyToDomainObject(restauranteInput, restauranteAtual);
		
		try {
			return restauranteModelOutAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}					
	}
	
	@PatchMapping("/{restauranteId}")
	public RestauranteModelOutput atualizarPorCampos(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request){		
		Restaurante restauranteAtual = cadastroRestauranteService.buscarThrow(restauranteId);
		
		merge(campos, restauranteAtual, request);
		
		validate(restauranteAtual, "restaurante");
		
		return atualizar(restauranteId, restauranteModelOutAssembler.toModelInput(restauranteAtual));
	}
	
	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		
		validator.validate(restaurante, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoPatchException(bindingResult);
		}
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.inativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{restauranteId}")
	public void remover(@PathVariable Long restauranteId){
		cadastroRestauranteService.remover(restauranteId);		
	}
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abreRestaurante(@PathVariable Long restauranteId) {
		cadastroRestauranteService.abrirRestaurante(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechaRestaurante(@PathVariable Long restauranteId) {
		cadastroRestauranteService.fechaRestaurante(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestauranteService.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}		
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestauranteService.inativar(restauranteIds);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
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
