package org.example.dotoli.config.error.exception;

import org.example.dotoli.config.error.ErrorCode;

import lombok.Getter;

@Getter
public class BusinessBaseException extends RuntimeException {

	private final ErrorCode errorCode;

	public BusinessBaseException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public BusinessBaseException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

}