package org.example.dotoli.config.error.exception;

import org.example.dotoli.config.error.ErrorCode;

/**
 * 접근 권한이 없는 작업에 대한 예외를 표현하는 클래스
 */
public class ForbiddenException extends BusinessBaseException {

	public ForbiddenException() {
		super(ErrorCode.FORBIDDEN);
	}

	public ForbiddenException(String message) {
		super(ErrorCode.FORBIDDEN, message);
	}

}