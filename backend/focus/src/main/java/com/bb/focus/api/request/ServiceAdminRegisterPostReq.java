package com.bb.focus.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * 서비스 관리자 계정 생성 API ([POST] /serviceusers/) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ServiceAdminRegisterPostRequest")
public class ServiceAdminRegisterPostReq {
    @NotBlank(message = "유저 아이디를 입력해주세요.")
    @ApiModelProperty(name = "유저 ID", example = "bb_bokhi")
    String userId;

    @NotBlank(message = "유저 패스워드를 입력해주세요.")
    @ApiModelProperty(name = "유저 Password", example = "123")
    String password;

    @NotBlank(message = "유저 이름을 입력해주세요.")
    @ApiModelProperty(name = "유저 이름", example = "복희")
    String name;

    @Pattern(message = "전화번호 형식을 준수해주세요.", regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$")
    @ApiModelProperty(name = "유저 전화 번호", example = "010-1234-1234")
    String tel;

    @Email(message = "이메일 형식을 준수해주세요.", regexp = "^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$")
    @ApiModelProperty(name = "유저 이메일", example = "bokhi@gmail.com")
    String email;
    @NotNull(message = "유저 권한을 입력해주세요.")
    @ApiModelProperty(name = "유저 권한", example = "1")
    Byte userRole;
}
