package com.algaworks.algafood;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaAPITestIT {
	
	@LocalServerPort
	private int porta;	
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();//exibe os passos do log no console para ajudar a identificar o erro
		RestAssured.port = porta;
		RestAssured.basePath = "/cozinhas";

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
	public void testarRetornoQtdeCozinhasGetCozinha() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(7))
			.body("nome", Matchers.hasItems("Brasileira","Italiana"));
	}
	
	@Test
	public void testarRetornoStatus201PostCozinha() {
		RestAssured.given()
			.body("{\"nome\": \"Chinesa\"}")
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void testarRetornoCozinhaGetBuscaCozinhaId(){
		RestAssured.given()
			.pathParam("cozinhaId", 2)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo("Americana"));
	}
	

	@Test
	public void testarRetornoNotFoundCozinhaGetBuscaCozinhaId(){
		RestAssured.given()
			.pathParam("cozinhaId", 999)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
}
