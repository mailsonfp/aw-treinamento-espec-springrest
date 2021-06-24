package com.algaworks.algafood;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaAPITestIT {
	
	@LocalServerPort
	private int porta;	
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	private String jsonCozinhaChinesa;
	Cozinha cozinhaGet;
	
	private static final int COZINHA_ID_INEXISTENTE = 999;	
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();//exibe os passos do log no console para ajudar a identificar o erro
		RestAssured.port = porta;
		RestAssured.basePath = "/cozinhas";
		
		jsonCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
		
		databaseCleaner.clearTables();
		
		cozinhaGet = new Cozinha();
		cozinhaGet.setNome("Brasileira");
		cozinhaGet = cozinhaRepository.save(cozinhaGet);
	}
	
	@Test
	public void testarRetornoStatus200GetCozinha() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void testarRetornoStatus201PostCozinha() {
		RestAssured.given()
			.body(jsonCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void testarRetornoCozinhaGetBuscaCozinhaId(){
		RestAssured.given()
			.pathParam("cozinhaId", cozinhaGet.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo(cozinhaGet.getNome()));
	}
	

	@Test
	public void testarRetornoNotFoundCozinhaGetBuscaCozinhaId(){
		RestAssured.given()
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
}
