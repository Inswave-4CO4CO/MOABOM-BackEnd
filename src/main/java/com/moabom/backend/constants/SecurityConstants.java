package com.moabom.backend.constants;

public final class SecurityConstants {
    //헤더 이름
    public static final String TOKEN_HEADER="Authorization";

    //토큰 접두사
    public static final String TOKEN_PREFIX="Bearer "; //Bearer공백${jwt}

    //토근 타입을 정의
    public static final String TOKEN_TYPE="JWT";
}