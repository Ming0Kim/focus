package com.bb.focus.api.response;

import com.bb.focus.db.entity.admin.ServiceAdmin;
import com.bb.focus.db.entity.company.CompanyAdmin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


/**
 * 서비스 계정 정보 조회 API ([GET] /serviceusers/accounts/) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ServiceAdminResponse")
public class ServiceAdminRes {
    @ApiModelProperty(name = "User ID")
    String userId;

    @ApiModelProperty(name = "Company Tel")
    String tel;

    @ApiModelProperty(name = "Company Email")
    String email;

    @ApiModelProperty(name = "Admin Name")
    String name;


    public static ServiceAdminRes of(ServiceAdmin user) {
        ServiceAdminRes res = new ServiceAdminRes();
        res.setUserId(user.getUserId());
        res.setTel(user.getTel());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        return res;
    }
}
