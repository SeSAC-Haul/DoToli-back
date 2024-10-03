package org.example.dotoli.config.error.exception;

import org.example.dotoli.config.error.ErrorCode;

/**
 * Task 항목을 찾을 수 없는 예외를 표현하는 클래스
 */
public class TaskNotFoundException extends BusinessBaseException {

    public TaskNotFoundException() {
        super(ErrorCode.TASK_NOT_FOUND);
    }

    public TaskNotFoundException(String message) {
        super(ErrorCode.TASK_NOT_FOUND, message);
    }


}
