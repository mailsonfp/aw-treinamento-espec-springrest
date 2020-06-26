package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		cozinhaRepository.salvar(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Italiana");
		cozinhaRepository.salvar(cozinha2);
		
		Cozinha cozinha3 = new Cozinha();
		cozinha3.setId(4L);
		cozinhaRepository.remover(cozinha3);
		
		for(Cozinha cozinha : cozinhaRepository.listar() ) {
			System.out.println("Cozinha: " + cozinha.getId() + " - " + cozinha.getNome());
		}
	}
}
