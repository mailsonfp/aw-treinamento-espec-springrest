package com.algaworks.algafood.core.springfox;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.ExceptionHandler.Problema;
import com.algaworks.algafood.api.model.output.CidadeModelOutput;
import com.algaworks.algafood.api.model.output.CozinhaModelOutput;
import com.algaworks.algafood.api.model.output.EstadoModelOutput;
import com.algaworks.algafood.api.model.output.FormaPagamentoModelOutput;
import com.algaworks.algafood.api.model.output.GrupoModelOutput;
import com.algaworks.algafood.api.model.output.PedidoResumoModelOutput;
import com.algaworks.algafood.api.model.output.PermissaoModelOutput;
import com.algaworks.algafood.api.model.output.ProdutoModelOutput;
import com.algaworks.algafood.api.model.output.RestauranteBasicoModel;
import com.algaworks.algafood.api.model.output.UsuarioModelOutput;
import com.algaworks.algafood.api.openapi.model.CidadesModelOpenApi;
import com.algaworks.algafood.api.openapi.model.CozinhasPageModelOpenApi;
import com.algaworks.algafood.api.openapi.model.EstadosModelOpenApi;
import com.algaworks.algafood.api.openapi.model.FormasPagamentoModelOpenApi;
import com.algaworks.algafood.api.openapi.model.GruposModelOpenApi;
import com.algaworks.algafood.api.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PedidosPageResumoModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PermissoesModelOpenApi;
import com.algaworks.algafood.api.openapi.model.ProdutosModelOpenApi;
import com.algaworks.algafood.api.openapi.model.RestaurantesBasicoModelOpenApi;
import com.algaworks.algafood.api.openapi.model.UsuariosModelOpenApi;
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
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)			
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, CozinhaModelOutput.class), CozinhasPageModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, PedidoResumoModelOutput.class), PedidosPageResumoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, CidadeModelOutput.class), CidadesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, EstadoModelOutput.class),EstadosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, FormaPagamentoModelOutput.class),FormasPagamentoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, GrupoModelOutput.class),GruposModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, PermissaoModelOutput.class),PermissoesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, ProdutoModelOutput.class),ProdutosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),RestaurantesBasicoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, UsuarioModelOutput.class),UsuariosModelOpenApi.class))
				.apiInfo(getApiInfo())
				.tags(
					new Tag("Cidades", "Responsável por armazenar todos os endpoints pertencentes a cidades"),
					new Tag("Grupos", "Responsável por armazenar todos os endpoints pertencentes a Grupos de Usuário"),
					new Tag("Cozinhas", "Responsável por armazenar todos os endpoints pertencentes a Cozinhas"),
					new Tag("Pedidos", "Responsável por armazenar todos os endpoints pertencentes a Pedidos"),
			        new Tag("Formas de Pagamento", "Responsável por armazenar todos os endpoints pertencentes a Formas de Pagmento"),
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
