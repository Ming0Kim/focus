package com.bb.focus.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserRes {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TokenInfo {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Long refreshTokenExpirationTime;
    }
}
