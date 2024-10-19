package org.example.dotoli.config.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	BAD_REQUEST(HttpStatus.BAD_REQUEST, "E1", "잘못된 요청입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E2", "서버 내부에서 오류가 발생했습니다."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "E3", "요청한 리소스를 찾을 수 없습니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "E4", "해당 리소스에 대한 접근 권한이 없습니다."),
	INVALID_INPUT(HttpStatus.BAD_REQUEST, "E5", "입력값이 올바르지 않습니다."),

	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M1", "회원 정보가 존재하지 않습니다."),
	DUPLICATE_EMAIL(HttpStatus.CONFLICT, "M2", "이미 사용 중인 이메일 주소입니다."),
	MEMBER_ALREADY_IN_TEAM(HttpStatus.CONFLICT, "M3", "이미 팀에 소속된 회원입니다."),

	DUPLICATE_TEAM_NAME(HttpStatus.CONFLICT, "TE1", "이미 사용 중인 팀 이름입니다."),
	TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "TE2", "팀 정보가 존재하지 않습니다."),

	TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "T1", "할 일 정보가 존재하지 않습니다."),

	DUPLICATE_INVITATION(HttpStatus.CONFLICT, "I1", "이미 대기 중인 초대가 있습니다."),
	INVITATION_NOT_FOUND(HttpStatus.NOT_FOUND, "I2", "초대 정보를 찾을 수 없습니다.");

	private final HttpStatus status;

	private final String code;

	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

}
