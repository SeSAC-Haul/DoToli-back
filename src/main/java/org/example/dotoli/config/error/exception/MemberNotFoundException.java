package org.example.dotoli.config.error.exception;

import org.example.dotoli.config.error.ErrorCode;

public class MemberNotFoundException extends BusinessBaseException {

	public MemberNotFoundException() {
		super(ErrorCode.MEMBER_NOT_FOUND);
	}

	public MemberNotFoundException(String message) {
		super(ErrorCode.MEMBER_NOT_FOUND, message);
	}

}
