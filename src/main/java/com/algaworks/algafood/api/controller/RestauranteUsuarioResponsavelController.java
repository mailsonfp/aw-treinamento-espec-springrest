package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.output.UsuarioModelOutputAssembler;
import com.algaworks.algafood.api.model.output.UsuarioModelOutput;
import com.algaworks.algafood.api.util.AlgaLinks;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;
    
    @Autowired
    private UsuarioModelOutputAssembler usuarioModelOut;
    
    @Autowired
    private AlgaLinks algaLinks;
    
    @GetMapping
    public CollectionModel<UsuarioModelOutput> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarThrow(restauranteId);
        
        return usuarioModelOut.toCollectionModel(restaurante.getResponsaveis())
        		.removeLinks()
        		.add(algaLinks.linkToResponsaveisRestaurante(restauranteId));
    }
    
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
    }
    
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
    }
}
