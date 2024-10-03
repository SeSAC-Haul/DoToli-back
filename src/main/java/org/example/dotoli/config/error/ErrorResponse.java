package org.example.dotoli.config.error;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private final String message;

	private final String code;

	private ErrorResponse(ErrorCode code) {
		this.message = code.getMessage();
		this.code = code.getCode();
	}

	private ErrorResponse(ErrorCode code, String message) {
		this.message = message;
		this.code = code.getCode();
	}

	public static ErrorResponse of(ErrorCode code) {
		return new ErrorResponse(code);
	}

	public static ErrorResponse of(ErrorCode code, String message) {
		return new ErrorResponse(code, message);
	}

}