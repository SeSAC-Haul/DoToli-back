package org.example.dotoli.config.error.exception;

import org.example.dotoli.config.error.ErrorCode;

public class DuplicateInvitationException extends BusinessBaseException {

	public DuplicateInvitationException() {
		super(ErrorCode.DUPLICATE_INVITATION);
	}

	public DuplicateInvitationException(String message) {
		super(ErrorCode.DUPLICATE_INVITATION, message);
	}

}
