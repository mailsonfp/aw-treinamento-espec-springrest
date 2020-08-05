package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.output.PermissaoModelOutputAssembler;
import com.algaworks.algafood.api.model.output.PermissaoModelOutput;
import com.algaworks.algafood.core.security.annotations.CheckSecurityUsuariosGruposPermissoes;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private PermissaoModelOutputAssembler permissaoModelOut;
	
	@CheckSecurityUsuariosGruposPermissoes.PermiteConsultar
	@GetMapping
	public List<PermissaoModelOutput> listar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		
		return permissaoModelOut.toCollectionModel(grupo.getPermissoes());
	}
	
	@CheckSecurityUsuariosGruposPermissoes.PermiteEditar
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
	}

	@CheckSecurityUsuariosGruposPermissoes.PermiteEditar
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.associarPermissao(grupoId, permissaoId);
	}

}