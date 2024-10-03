package org.example.dotoli.config.error;

import org.example.dotoli.config.error.exception.BusinessBaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessBaseException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessBaseException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode(), e.getMessage());
		return new ResponseEntity<>(errorResponse, e.getErrorCode().getStatus());
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(errorResponse, ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
	}

}