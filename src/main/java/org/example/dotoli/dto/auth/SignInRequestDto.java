package org.example.dotoli.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 로그인 요청 정보를 담는 DTO 클래스
 */
@Data
public class SignInRequestDto {

	@NotBlank(message = "이메일은 필수 입력값입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수 입력값입니다.")
	@Size(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이하여야 합니다.")
	private String password;

}
