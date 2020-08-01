package com.algaworks.algafood.core.security;

import java.util.Arrays;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	private final List<String> COMPLEMENTO_CHAVE = Arrays.asList("a","b","c","d","e","f","g","h","i","j","l","m","n","o","p","q","r","t","u","v","x","z","w","y","k");
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()				
				.anyRequest().authenticated()
			.and()
			.cors().and()
			.oauth2ResourceServer().jwt();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		var secretKey = new SecretKeySpec(montaChave("mailsonfp").getBytes(), "HmacSHA256");
		return NimbusJwtDecoder.withSecretKey(secretKey).build();
	}
	
	private String montaChave(String chaveInicial) {
		StringBuilder chaveRetorno = new StringBuilder(chaveInicial);
		
		COMPLEMENTO_CHAVE.forEach(l -> chaveRetorno.append(l));
				
		return chaveRetorno.toString();
	}
}
