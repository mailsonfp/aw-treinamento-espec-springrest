package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.input.UsuarioModelInputAssembler;
import com.algaworks.algafood.api.v1.assembler.output.UsuarioModelOutputAssembler;
import com.algaworks.algafood.api.v1.model.input.UsuarioModelInputCompleto;
import com.algaworks.algafood.api.v1.model.input.UsuarioModelInputDados;
import com.algaworks.algafood.api.v1.model.input.UsuarioModelInputSenha;
import com.algaworks.algafood.api.v1.model.output.UsuarioModelOutput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {
	@Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CadastroUsuarioService cadastroUsuario;
    
    @Autowired
    private UsuarioModelOutputAssembler usuarioModelOut;
    
    @Autowired
    private UsuarioModelInputAssembler usuarioModelIn;
    
    @GetMapping
    public CollectionModel<UsuarioModelOutput> listar() {
        List<Usuario> todasUsuarios = usuarioRepository.findAll();
        
        return usuarioModelOut.toCollectionModel(todasUsuarios);
    }
    
    @GetMapping("/{usuarioId}")
    public UsuarioModelOutput buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscarThrow(usuarioId);
        
        return usuarioModelOut.toModel(usuario);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModelOutput adicionar(@RequestBody @Valid UsuarioModelInputCompleto usuarioInput) {
        Usuario usuario = usuarioModelIn.toDomainObject(usuarioInput);
        usuario = cadastroUsuario.salvar(usuario);
        
        return usuarioModelOut.toModel(usuario);
    }
    
    @PutMapping("/{usuarioId}")
    public UsuarioModelOutput atualizar(@PathVariable Long usuarioId,
            @RequestBody @Valid UsuarioModelInputDados usuarioInput) {
        Usuario usuarioAtual = cadastroUsuario.buscarThrow(usuarioId);
        usuarioModelIn.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);
        
        return usuarioModelOut.toModel(usuarioAtual);
    }
    
    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioModelInputSenha senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}
