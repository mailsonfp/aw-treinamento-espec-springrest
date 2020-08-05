package com.algaworks.algafood.api.controller;

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

import com.algaworks.algafood.api.assembler.input.UsuarioModelInputAssembler;
import com.algaworks.algafood.api.assembler.output.UsuarioModelOutputAssembler;
import com.algaworks.algafood.api.model.input.UsuarioModelInputCompleto;
import com.algaworks.algafood.api.model.input.UsuarioModelInputDados;
import com.algaworks.algafood.api.model.input.UsuarioModelInputSenha;
import com.algaworks.algafood.api.model.output.UsuarioModelOutput;
import com.algaworks.algafood.core.security.annotations.CheckSecurityUsuariosGruposPermissoes;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CadastroUsuarioService cadastroUsuario;
    
    @Autowired
    private UsuarioModelOutputAssembler usuarioModelOut;
    
    @Autowired
    private UsuarioModelInputAssembler usuarioModelIn;
    
    @CheckSecurityUsuariosGruposPermissoes.PermiteConsultar
    @GetMapping
    public CollectionModel<UsuarioModelOutput> listar() {
        List<Usuario> todasUsuarios = usuarioRepository.findAll();
        
        return usuarioModelOut.toCollectionModel(todasUsuarios);
    }
    
    @CheckSecurityUsuariosGruposPermissoes.PermiteConsultar
    @GetMapping("/{usuarioId}")
    public UsuarioModelOutput buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscarThrow(usuarioId);
        
        return usuarioModelOut.toModel(usuario);
    }
    
    @CheckSecurityUsuariosGruposPermissoes.PermiteEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModelOutput adicionar(@RequestBody @Valid UsuarioModelInputCompleto usuarioInput) {
        Usuario usuario = usuarioModelIn.toDomainObject(usuarioInput);
        usuario = cadastroUsuario.salvar(usuario);
        
        return usuarioModelOut.toModel(usuario);
    }
    
    @CheckSecurityUsuariosGruposPermissoes.PermiteAlterarUsuario
    @PutMapping("/{usuarioId}")
    public UsuarioModelOutput atualizar(@PathVariable Long usuarioId,
            @RequestBody @Valid UsuarioModelInputDados usuarioInput) {
        Usuario usuarioAtual = cadastroUsuario.buscarThrow(usuarioId);
        usuarioModelIn.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);
        
        return usuarioModelOut.toModel(usuarioAtual);
    }
    
    @CheckSecurityUsuariosGruposPermissoes.PermiteAlterarPropriaSenha
    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioModelInputSenha senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}
