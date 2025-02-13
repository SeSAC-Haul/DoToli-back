package org.example.dotoli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/health")
public class HealthCheckController {

	@GetMapping
	public ResponseEntity<String> simpleCheck() {
		return ResponseEntity.ok("OK");
	}

}
