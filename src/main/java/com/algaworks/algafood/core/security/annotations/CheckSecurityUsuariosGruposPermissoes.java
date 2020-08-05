package com.algaworks.algafood.core.security.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurityUsuariosGruposPermissoes {
	
	@PreAuthorize("hasAuthority('SCOPE_WRITE') and "
            + "@algaSecurity.checaUsuarioLogado(#usuarioId)")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PermiteAlterarPropriaSenha { }
    
    @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
            + "@algaSecurity.checaUsuarioLogado(#usuarioId))")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PermiteAlterarUsuario { }

    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PermiteEditar { }
    

    @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PermiteConsultar { }
    
}
