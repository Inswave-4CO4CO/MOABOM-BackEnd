package com.moabom.backend.auth.constants;

public final class SecurityConstants {
    //헤더 이름
    public static final String TOKEN_HEADER="Authorization";

    //토큰 접두사
    public static final String TOKEN_PREFIX="Bearer "; //Bearer공백${jwt}

    // 1분
    public static final long ACCESS_TOKEN_EXPIRE = 1000 * 60 * 1;
    public static final long REFRESH_TOKEN_EXPIRE = 1000 * 60 * 60 * 24 * 14;
}