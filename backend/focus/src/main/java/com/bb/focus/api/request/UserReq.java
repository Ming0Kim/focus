package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 관련 API ([POST] /login) 요청에 필요한 리퀘스트 바디 정의.
 */

public class UserReq {

    @Getter
    @Setter
    public static class Login {

        @NotBlank(message = "유저 아이디를 입력해주세요.")
        @ApiModelProperty(name = "유저 ID", example = "bb_cherry")
        String id;

        @NotBlank(message = "유저의 패스워드를 입력해주세요.")
        @ApiModelProperty(name = "유저 Password", example = "123")
        String password;

    }

    @Getter
    @Setter
    public static class Logout {

        @NotBlank(message = "유저의 액세스 토큰을 입력해주세요.")
        @ApiModelProperty(name="유저의 액세스 토큰", example = "Bearer dslfisjfkdsl..")
        private String accessToken;

    }

}
