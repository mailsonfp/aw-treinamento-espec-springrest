package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.ExceptionHandler.Problema;
import com.algaworks.algafood.api.model.output.CozinhaModelOutput;
import com.algaworks.algafood.api.model.output.PedidoResumoModelOutput;
import com.algaworks.algafood.api.openapi.model.CozinhasPageModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PedidosPageResumoModelOpenApi;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {
	
	ModelRef modeloProblema = new ModelRef("Problema");
	
	@Bean
	public Docket apiDocket() {
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
//					.paths(PathSelectors.any())
//					.paths(PathSelectors.ant("/restaurantes/*"))
					.build()
				.globalResponseMessage(RequestMethod.GET, getGlobalResponseMessage())
				.globalResponseMessage(RequestMethod.POST, getGlobalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, getGlobalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, getGlobalDeleteResponseMessages())
				/*.globalOperationParameters(Arrays.asList(
						new ParameterBuilder()
							.name("campos")
							.description("propriedades que devem ser retornadas na resposta(ex: nome,telefone)")
							.parameterType("query")
							.modelRef(new ModelRef("string"))
							.build()))*/
				.useDefaultResponseMessages(false)
				.additionalModels(typeResolver.resolve(Problema.class))
				.ignoredParameterTypes(ServletRequest.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, CozinhaModelOutput.class), CozinhasPageModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, PedidoResumoModelOutput.class), PedidosPageResumoModelOpenApi.class))
				.apiInfo(getApiInfo())
				.tags(
					new Tag("Cidades", "Responsável por armazenar todos os endpoints pertencentes a cidades"),
					new Tag("Grupos", "Responsável por armazenar todos os endpoints pertencentes a Grupos de Usuário"),
					new Tag("Cozinhas", "Responsável por armazenar todos os endpoints pertencentes a Cozinhas"),
			        new Tag("Formas de pagamento", "Responsável por armazenar todos os endpoints pertencentes a Formas de Pagmento"),
			        new Tag("Restaurantes", "Responsável por armazenar todos os enpoints pertencentes a restaurantes"),
			        new Tag("Produtos", "Responsável por armazenar todos os enpoints pertencentes a restaurantes"),
			        new Tag("Relatórios", "Responsável por armazenar todos os endpoints que retornam relatórios"));
	}
	
	private List<ResponseMessage> getGlobalResponseMessage(){
		return Arrays.asList(
					new ResponseMessageBuilder()
						.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno da api")
						.responseModel(modeloProblema)
						.build(),
					new ResponseMessageBuilder()
						.code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso  não possui representação que poderia ser aceita pelo consumidor")
						.build()
				);
	}
	
	private List<ResponseMessage> getGlobalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisição inválida")
					.responseModel(modeloProblema)
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno no servidor")
					.responseModel(modeloProblema)
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
					.message("Requisição recusada porque o corpo está em um formato não suportado")
					.responseModel(modeloProblema)
					.build()
			);
	}
	
	private List<ResponseMessage> getGlobalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisição inválida")
					.responseModel(modeloProblema)
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno no servidor")
					.responseModel(modeloProblema)
					.build()
			);
	}
	
	public ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
					.title("Especialista Spring Rest - Algafood API")
					.description("API desenvolvida no treinamento Especialista Spring Rest")
					.version("1")
					.contact(new Contact("Mailson", "https://www.linkedin.com/in/mailsonfernandopereira/", "mailsonp.dev@gmail.com"))
					.build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
	
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
