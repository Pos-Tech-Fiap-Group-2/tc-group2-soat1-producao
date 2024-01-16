package com.techchallenge.producao.adapter.driver.exceptionhandler;


import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String ERRO_INESPERADO = "Ocorreu um erro interno inesperado no sistema.";
	private static final String CORPO_REQUISICAO_INVALIDO = "Corpo da requisição está inválido. Verifique erro de sintaxe";
	private static final String CAMPOS_INVALIDOS = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

	@Autowired
	private MessageSource messageSource;
	
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		ProblemType problemType = ProblemType.MENSAGEM_INCONSISTENTE;
		String detail = CORPO_REQUISICAO_INVALIDO;
		
		Problem problem = this.createProblemBuilder(status, problemType, detail).build();
		
		return this.handleExceptionInternal(ex, problem, new HttpHeaders(), 
				status, request);
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = CAMPOS_INVALIDOS;

		List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

			String name = objectError.getObjectName();

			return Problem.Object.builder().name(name).userMessage(message).build();
		}).collect(Collectors.toList());

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).objects(problemObjects)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
	    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
	    ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
	    
	    String detail = ERRO_INESPERADO;
	    Problem problem = createProblemBuilder(status, problemType, detail).build();

	    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode status, ProblemType problemType, String detail) {
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail).timestamp(OffsetDateTime.now());
	}
}