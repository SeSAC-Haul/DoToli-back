package org.example.dotoli.config.error;

import org.example.dotoli.config.error.exception.BusinessBaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessBaseException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessBaseException e) {
		log.error("Business exception occurred: {}", e.getMessage(), e);
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode(), e.getMessage());
		return new ResponseEntity<>(errorResponse, e.getErrorCode().getStatus());
	}

	@ExceptionHandler(NoResourceFoundException.class)
	protected ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NO_RESOURCE_FOUND, e.getMessage());
		return new ResponseEntity<>(errorResponse, ErrorCode.NO_RESOURCE_FOUND.getStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		StringBuilder errorMessage = new StringBuilder("유효성 검사 실패: ");

		for (FieldError fieldError : result.getFieldErrors()) {
			errorMessage.append(fieldError.getField()).append("(").append(fieldError.getDefaultMessage()).append("), ");
		}

		log.warn("Validation failed: {}", errorMessage);
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT, errorMessage.toString());
		return new ResponseEntity<>(errorResponse, ErrorCode.INVALID_INPUT.getStatus());
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error("Unhandled exception occurred: ", e);
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(errorResponse, ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
	}

}
