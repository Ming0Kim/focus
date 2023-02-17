package com.bb.focus.api.response;

import com.bb.focus.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([POST] /login) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserLoginPostResponse")
public class UserLoginPostRes extends BaseResponseBody {
    @ApiModelProperty(name = "JWT 인증 토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
    String accessToken;

    @ApiModelProperty(name = "유저 role")
    Byte userRole;


    @ApiModelProperty(name = "유저 시퀀스 넘버")
    Long id;

    public static UserLoginPostRes of(Integer statusCode, String message, String accessToken, Byte userRole, Long id) {
        UserLoginPostRes res = new UserLoginPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAccessToken(accessToken);
        res.setUserRole(userRole);
        res.setId(id);
        return res;
    }
}
