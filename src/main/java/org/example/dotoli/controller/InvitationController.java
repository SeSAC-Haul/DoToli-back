package org.example.dotoli.controller;

import org.example.dotoli.dto.invitation.InvitationRequestDto;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.example.dotoli.service.InvitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams/{teamId}/invitations")
public class InvitationController {

	private final InvitationService invitationService;

	@PostMapping
	public ResponseEntity<Long> invite(
			@PathVariable("teamId") Long teamId,
			@RequestBody InvitationRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(invitationService.createInvitation(dto, userDetails.getMember().getId(), teamId));
	}

}
