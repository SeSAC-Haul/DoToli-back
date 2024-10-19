package org.example.dotoli.config.error.exception;

import org.example.dotoli.config.error.ErrorCode;

public class InvitationNotFoundException extends BusinessBaseException {

	public InvitationNotFoundException() {
		super(ErrorCode.INVITATION_NOT_FOUND);
	}

	public InvitationNotFoundException(String message) {
		super(ErrorCode.INVITATION_NOT_FOUND, message);
	}

}
