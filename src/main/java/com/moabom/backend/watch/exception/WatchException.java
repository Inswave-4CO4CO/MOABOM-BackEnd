package com.moabom.backend.watch.exception;

import com.moabom.backend.watch.controller.WatchController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//WatchController에 대한 예외 처리
@ControllerAdvice(assignableTypes = {WatchController.class})
public class WatchException {
    //충돌이 생겼을 때
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    //잘못된 인자 전달
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); // 잘못된 인자 같은 상황에 BAD_REQUEST

    }
}
