package org.example.dotoli.config.error.exception;

import org.example.dotoli.config.error.ErrorCode;

/**
 * 중복된 로그인 ID 예외를 표현하는 클래스
 */
public class DuplicateEmailException extends BusinessBaseException {

	public DuplicateEmailException() {
		super(ErrorCode.DUPLICATE_EMAIL);
	}

	public DuplicateEmailException(String message) {
		super(ErrorCode.DUPLICATE_EMAIL, message);
	}

}