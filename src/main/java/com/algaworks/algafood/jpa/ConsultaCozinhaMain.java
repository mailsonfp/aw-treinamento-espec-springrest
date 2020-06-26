package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;

public class ConsultaCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		cadastroCozinha.adicionar(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Italiana");
		cadastroCozinha.adicionar(cozinha2);
		
		for(Cozinha cozinha : cadastroCozinha.listar() ) {
			System.out.println("Cozinha: " + cozinha.getId() + " - " + cozinha.getNome());
		}
	}
}
