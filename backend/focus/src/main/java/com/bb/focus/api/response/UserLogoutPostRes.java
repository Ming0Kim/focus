package com.bb.focus.api.response;

import com.bb.focus.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그아웃 API ([POST] /login) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserLogoutPostResponse")
public class UserLogoutPostRes extends BaseResponseBody {
    @ApiModelProperty(name = "사용자 아이디", example = "bb_user")
    String userId;

    public static UserLogoutPostRes of(Integer statusCode, String message, String userId) {
        UserLogoutPostRes res = new UserLogoutPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setUserId(userId);
        return res;
    }
}
