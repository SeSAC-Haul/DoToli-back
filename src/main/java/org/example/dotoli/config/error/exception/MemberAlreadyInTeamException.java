package org.example.dotoli.config.error.exception;

import org.example.dotoli.config.error.ErrorCode;

public class MemberAlreadyInTeamException extends BusinessBaseException {

	public MemberAlreadyInTeamException() {
		super(ErrorCode.MEMBER_ALREADY_IN_TEAM);
	}

	public MemberAlreadyInTeamException(String message) {
		super(ErrorCode.MEMBER_ALREADY_IN_TEAM, message);
	}

}
