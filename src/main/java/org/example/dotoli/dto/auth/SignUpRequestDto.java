package org.example.dotoli.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 회원가입 요청 정보를 담는 DTO 클래스
 */
@Data
public class SignUpRequestDto {

	@NotBlank(message = "이메일은 필수 입력값입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수 입력값입니다.")
	@Size(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이하여야 합니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
			message = "비밀번호는 영문자와 숫자를 모두 포함해야 합니다.")
	private String password;

	@NotBlank(message = "닉네임은 필수 입력값입니다.")
	@Size(min = 2, max = 8, message = "닉네임은 2자 이상 8자 이하여야 합니다.")
	private String nickname;

}
