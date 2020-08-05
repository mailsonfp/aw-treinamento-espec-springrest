package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.input.GrupoModelInputAssembler;
import com.algaworks.algafood.api.assembler.output.GrupoModelOutputAssembler;
import com.algaworks.algafood.api.model.input.GrupoModelInput;
import com.algaworks.algafood.api.model.output.GrupoModelOutput;
import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.core.security.annotations.CheckSecurityUsuariosGruposPermissoes;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;
    
    @Autowired
    private CadastroGrupoService cadastroGrupo;
    
    @Autowired
    private GrupoModelOutputAssembler grupoModelOut;
    
    @Autowired
    private GrupoModelInputAssembler grupoModeIn;
    
    @CheckSecurityUsuariosGruposPermissoes.PermiteConsultar
    @GetMapping
    public List<GrupoModelOutput> listar() {
        List<Grupo> todosGrupos = grupoRepository.findAll();
        
        return grupoModelOut.toCollectionModel(todosGrupos);
    }
    
    @CheckSecurityUsuariosGruposPermissoes.PermiteConsultar
    @GetMapping("/{grupoId}")
    public GrupoModelOutput buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
        
        return grupoModelOut.toModel(grupo);
    }
    
    @CheckSecurityUsuariosGruposPermissoes.PermiteEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModelOutput adicionar(@RequestBody @Valid GrupoModelInput grupoInput) {
        Grupo grupo = grupoModeIn.toDomainObject(grupoInput);
        
        grupo = cadastroGrupo.salvar(grupo);
        
        return grupoModelOut.toModel(grupo);
    }
    
    @CheckSecurityUsuariosGruposPermissoes.PermiteEditar
    @PutMapping("/{grupoId}")
    public GrupoModelOutput atualizar(@PathVariable Long grupoId,
            @RequestBody @Valid GrupoModelInput grupoInput) {
        Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);
        
        grupoModeIn.copyToDomainObject(grupoInput, grupoAtual);
        
        grupoAtual = cadastroGrupo.salvar(grupoAtual);
        
        return grupoModelOut.toModel(grupoAtual);
    }
    
    @CheckSecurityUsuariosGruposPermissoes.PermiteEditar
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupo.remover(grupoId);	
    }   
}