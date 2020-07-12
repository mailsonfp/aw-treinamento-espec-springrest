package com.algaworks.algafood.api.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoPatchException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MSG_ERRO_GENERICO = "Ocorreu um erro interno inesperado e não foi posível completar a requisição.";
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problema problema = new Problema(
					status.value(),
					ProblemType.RECURSO_NAO_ENCONTRADO.getUri(),
					ProblemType.RECURSO_NAO_ENCONTRADO.getTitle(),
					ex.getMessage(),
					ex.getMessage());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = new Problema(
					status.value(),
					ProblemType.ERRO_NEGOCIO.getUri(),
					ProblemType.ERRO_NEGOCIO.getTitle(),
					ex.getMessage(),
					ex.getMessage());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
		HttpStatus status = HttpStatus.CONFLICT;
		
		Problema problema = new Problema(
					status.value(),
					ProblemType.ENTIDADE_EM_USO.getUri(),
					ProblemType.ENTIDADE_EM_USO.getTitle(),
					ex.getMessage(),
					ex.getMessage());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {		
	    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
	    
	    Problema problema = new Problema(
				status.value(),
				ProblemType.ERRO_DE_SISTEMA.getUri(),
				ProblemType.ERRO_DE_SISTEMA.getTitle(),
				MSG_ERRO_GENERICO,
				MSG_ERRO_GENERICO);
	    
	    // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
	    // fazendo logging) para mostrar a stacktrace no console
	    // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
	    // para você durante, especialmente na fase de desenvolvimento
	    ex.printStackTrace();
	    	    
	    return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
	        HttpHeaders headers, HttpStatus status, WebRequest request) {
	    
	    Problema problema = new Problema(
				status.value(),
				ProblemType.RECURSO_NAO_ENCONTRADO.getUri(),
				ProblemType.RECURSO_NAO_ENCONTRADO.getTitle(),
				String.format("O recurso %s não existe.", ex.getRequestURL()),
				MSG_ERRO_GENERICO);
	    
	    return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		Problema problema = retornaProblemaObjetosInvalidos(ex.getBindingResult(),
				status,
				ProblemType.PARAMETRO_INVALIDO.getUri(),
				ProblemType.PARAMETRO_INVALIDO.getTitle(),
				"Um ou mais parâmetros para realizar a consulta são inválidos.");		
		
	    return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {
		Problema problema = retornaProblemaObjetosInvalidos(ex.getBindingResult(),
				status,
				ProblemType.JSON_INVALIDO.getUri(),
				ProblemType.JSON_INVALIDO.getTitle(),
				String.format("O json enviado no corpo da requisição não possui todos os campos obrigatórios."));		
		
	    return handleExceptionInternal(ex, problema, headers, status, request);
	}  
	
	@ExceptionHandler({ ValidacaoPatchException.class })
	public ResponseEntity<Object> handleValidacaoException(ValidacaoPatchException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = retornaProblemaObjetosInvalidos(ex.getBindingResult(),
					status,
					ProblemType.JSON_INVALIDO.getUri(),
					ProblemType.JSON_INVALIDO.getTitle(),
					String.format("O json enviado no corpo da requisição não possui todos os campos obrigatórios."));
		
	    return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}  
	
	private Problema retornaProblemaObjetosInvalidos(BindingResult bindingResult, HttpStatus status, String uri, String title, String detail){
		
		List<Problema.ProblemaObjects> problemaField = bindingResult.getAllErrors().stream()
				.map(objectError -> {
					String mensagem = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					
					var nomeCampo = objectError.getObjectName();
					
					if(objectError instanceof FieldError) {
						nomeCampo = ((FieldError) objectError).getField();
					}
					return new Problema.ProblemaObjects(nomeCampo, mensagem);
				})
				.collect(Collectors.toList());
		
		Problema problema = new Problema(
				status.value(),
				uri,
				title,
				detail,
				detail,
				problemaField);	
		
		return problema;
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
	        return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
	    }
		
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Problema problema = new Problema(
				status.value(),
				ProblemType.PARAMETRO_INVALIDO.getUri(),
				ProblemType.PARAMETRO_INVALIDO.getTitle(),
				String.format("Parâmetro '%s': não é possível converter o valor '%s' para o tipo %s", ex.getName(), ex.getValue(), ex.getRequiredType()),
				MSG_ERRO_GENERICO);
	
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		}else if(rootCause instanceof IgnoredPropertyException) {
			return handleIgnoredProperty((IgnoredPropertyException) rootCause, headers, status, request);
		}else if(rootCause instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedProperty((UnrecognizedPropertyException) rootCause, headers, status, request);
		}
		
		Problema problema = new Problema(
					status.value(),
					ProblemType.JSON_INVALIDO.getUri(),
					ProblemType.JSON_INVALIDO.getTitle(),
					"O json enviado no corpo da requisição está com um formato inválido.",
					MSG_ERRO_GENERICO);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedProperty(UnrecognizedPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String path = joinPath(ex.getPath());
		
		Problema problema = new Problema(
				status.value(),
				ProblemType.JSON_INVALIDO.getUri(),
				ProblemType.JSON_INVALIDO.getTitle(),
				String.format("Propriedade '%s' não deve ser informada", path),
				MSG_ERRO_GENERICO);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	private ResponseEntity<Object> handleIgnoredProperty(IgnoredPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String path = joinPath(ex.getPath());
		
		Problema problema = new Problema(
				status.value(),
				ProblemType.JSON_INVALIDO.getUri(),
				ProblemType.JSON_INVALIDO.getTitle(),
				String.format("Propriedade '%s' não deve ser informada", path),
				MSG_ERRO_GENERICO);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String path = joinPath(ex.getPath());
		
		Problema problema = new Problema(
				status.value(),
				ProblemType.JSON_INVALIDO.getUri(),
				ProblemType.JSON_INVALIDO.getTitle(),
				String.format("Propriedade '%s': não é possível converter o valor '%s' para o tipo %s", path, ex.getValue(), ex.getTargetType()),
				MSG_ERRO_GENERICO);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if(body == null){
			body = new Problema(status.value(),null,status.getReasonPhrase(),"", MSG_ERRO_GENERICO);
		}else if(body instanceof String) {
			body = new Problema(status.value(),null, (String) body, "", MSG_ERRO_GENERICO);
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private String joinPath(List<Reference> references) {
	    return references.stream()
	        .map(ref -> ref.getFieldName())
	        .collect(Collectors.joining("."));
	}  
}
