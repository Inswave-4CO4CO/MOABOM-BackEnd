package com.moabom.backend.auth.constants;

public final class SecurityConstants {
    //헤더 이름
    public static final String TOKEN_HEADER="Authorization";

    //토큰 접두사
    public static final String TOKEN_PREFIX="Bearer "; //Bearer공백${jwt}

    // 초단위
    public static final long ACCESS_TOKEN_EXPIRE = 60L * 30; // 30분
    public static final long REFRESH_TOKEN_EXPIRE = 60L * 60 * 24 * 14; // 14일
}