package com.algaworks.algafood.core.security.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurityRestaurante {
	
	@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PermiteGerenciarCadastro { }
	
	@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_RESTAURANTES') or @algaSecurity.gerenciaRestaurante(#restauranteId))")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PermiteGerenciarFuncionamento { }
	
    @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PermiteConsultar { }
    
}
