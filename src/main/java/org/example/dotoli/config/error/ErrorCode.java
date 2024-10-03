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

	TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "T1", "할 일 정보가 존재하지 않습니다.");

	private final HttpStatus status;

	private final String code;

	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

}