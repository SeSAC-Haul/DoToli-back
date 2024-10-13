package org.example.dotoli.config.error.exception;

import org.example.dotoli.config.error.ErrorCode;

public class TeamNotFoundException extends BusinessBaseException {

	public TeamNotFoundException() {
		super(ErrorCode.TEAM_NOT_FOUND);
	}

	public TeamNotFoundException(String message) {
		super(ErrorCode.TEAM_NOT_FOUND, message);
	}

}
