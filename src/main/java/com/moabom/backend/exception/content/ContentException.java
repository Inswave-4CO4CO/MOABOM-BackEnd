package com.moabom.backend.exception.content;

import com.moabom.backend.controller.content.ContentController; // ContentController 임포트!
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//ContentController에 대한 예외처리
@ControllerAdvice(assignableTypes = {ContentController.class})
public class ContentException {

    //ContentNotFoundException 발생 시 처리
    @ExceptionHandler(ContentNotFoundException.class)
    public ResponseEntity<?> handleContentNotFoundException(ContentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
