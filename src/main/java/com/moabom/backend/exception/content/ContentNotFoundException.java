package com.moabom.backend.exception.content;

//커스텀 예외 처리 (콘텐츠를 찾을 수 없을 때 사용할 커스텀 예외)
public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(String message) {
        super(message);
    }
}
