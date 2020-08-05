package com.algaworks.algafood.core.security.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurityFormaPagamento {
	
	@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PermiteEditar { }

    @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PermiteConsultar { }
    
}
