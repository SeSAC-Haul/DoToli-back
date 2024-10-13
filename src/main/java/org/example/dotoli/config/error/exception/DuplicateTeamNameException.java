package org.example.dotoli.config.error.exception;

import org.example.dotoli.config.error.ErrorCode;

public class DuplicateTeamNameException extends BusinessBaseException {

	public DuplicateTeamNameException() {
		super(ErrorCode.DUPLICATE_TEAM_NAME);
	}

	public DuplicateTeamNameException(String message) {
		super(ErrorCode.DUPLICATE_TEAM_NAME, message);
	}

}
