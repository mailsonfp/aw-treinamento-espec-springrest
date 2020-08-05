package com.algaworks.algafood.core.security.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurityPedido {
	
	@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
	@PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
			+ "@algaSecurity.getUsuarioId() == returnObject.cliente.id or "
			+ "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PermiteBuscar { }
	
	@PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULTAR_PEDIDOS') or " 
			+ "@algaSecurity.getUsuarioId() == #filtro.clienteId or"
			+ "@algaSecurity.gerenciaRestaurante(#filtro.restauranteId))")
	@Retention(RUNTIME)
	@Target(METHOD)
	public @interface PermitePesquisar { }
	
	@PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
	@Retention(RUNTIME)
	@Target(METHOD)
	public @interface PermiteCriar { }

	@PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('GERENCIAR_PEDIDOS') or "
			+ "@algaSecurity.gerenciaRestauranteDoPedido(#codigoPedido))")
	@Retention(RUNTIME)
	@Target(METHOD)
	public @interface PermiteGerenciarPedidos {}
	
	@PreAuthorize("hasAuthority('SCOPE_READ') and "
            + "hasAuthority('GERAR_RELATORIOS')")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PermiteGerarRelatorios { }
}
