package com.algaworks.algafood.jpa;

import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class ConsultaCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);
		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
		CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		cozinhaRepository.salvar(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Italiana");
		cozinhaRepository.salvar(cozinha2);
		
		Cozinha cozinha3 = new Cozinha();
		cozinha3.setId(4L);
		cozinhaRepository.remover(cozinha3);
		
		System.out.println("\nCozinhas:");
		for(Cozinha cozinha : cozinhaRepository.listar() ) {
			System.out.println("Cozinha: " + cozinha.getId() + " - " + cozinha.getNome());
		}			
		
		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Centra Restaurante");
		restaurante1.setTaxaFrete(new BigDecimal(5.99));
		restaurante1.setCozinha(cozinhaRepository.buscar(3L));
		restauranteRepository.salvar(restaurante1);
		
		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Centra Restaurante");
		restaurante2.setTaxaFrete(new BigDecimal(5.99));
		restaurante2.setCozinha(cozinhaRepository.buscar(3L));
		restauranteRepository.salvar(restaurante2);
		
		Restaurante restaurante3 = new Restaurante();
		restaurante3.setId(4L);
		restauranteRepository.remover(restaurante3);
		
		System.out.println("\nRestaurantes:");
		for(Restaurante restaurante : restauranteRepository.listar() ) {
			System.out.println("Cozinha: " + restaurante.getId() + " - " + restaurante.getNome() + " - Cozinha: " + restaurante.getCozinha().getNome() );
		}
		
		System.out.println("\nPermissões:");
		for (Permissao permissao : permissaoRepository.listar()) {
            System.out.printf("%s - %s\n", permissao.getNome(), permissao.getDescricao());
        }
		
		System.out.println("\nFormas de Pagamento:");
		for (FormaPagamento formaPagamento : formaPagamentoRepository.listar()) {
            System.out.println(formaPagamento.getDescricao());
        }
		
		System.out.println("\nCidades:");
		for (Cidade cidade : cidadeRepository.listar()) {
            System.out.printf("%s - %s\n", cidade.getNome(), cidade.getEstado().getNome());
        }
	}
}
