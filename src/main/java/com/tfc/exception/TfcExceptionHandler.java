package com.tfc.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class TfcExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> customHandleNotFound(DataNotFoundException ex, WebRequest request) {

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setCode(HttpStatus.NOT_FOUND.value());

		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<CustomErrorResponse> customHandleNotFound(InvalidDataException ex, WebRequest request) {

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setCode(HttpStatus.FORBIDDEN.value());

		return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);

	}
	
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<CustomErrorResponse> customHandleNotFound(ApplicationException ex, WebRequest request) {

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError("Internal server error ");
		errors.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}