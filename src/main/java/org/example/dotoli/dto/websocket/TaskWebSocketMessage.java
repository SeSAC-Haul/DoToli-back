package org.example.dotoli.dto.websocket;

import org.example.dotoli.dto.task.TaskResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskWebSocketMessage {

	private String type;  // CREATE, UPDATE, DELETE

	private Long teamId;

	private TaskResponseDto task;

	private String email; // 작업 수행자

	private Long taskId;  // DELETE 시 사용

}
