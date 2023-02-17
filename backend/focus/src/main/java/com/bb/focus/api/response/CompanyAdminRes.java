package com.bb.focus.api.response;

import com.bb.focus.db.entity.company.CompanyAdmin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * 기업 계정 정보 조회 API ([GET] /serviceusers/accounts/) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("CompanyAdminResponse")
public class CompanyAdminRes {

    @ApiModelProperty(name = "User Sequence Number")
    Long id;

    @ApiModelProperty(name = "User ID")
    String userId;

    @ApiModelProperty(name = "Company Name")
    String companyName;

    @ApiModelProperty(name = "Contract Start Date")
    LocalDateTime startDate;

    @ApiModelProperty(name = "Contract End Date")
    LocalDateTime endDate;

    @ApiModelProperty(name = "Company Tel")
    String tel;

    @ApiModelProperty(name = "Company Email")
    String email;

    @ApiModelProperty(name = "Is Expired")
    boolean isExpired;

    @ApiModelProperty(name = "Company Admin Name")
    String name;

    @ApiModelProperty(name = "Industry")
    String industry;

    @ApiModelProperty(name = "Company Size")
    String size;

    @ApiModelProperty(name = "Company Logo Image")
    String logoImage;

    @ApiModelProperty(name = "Interview Room Skin Color")
    String skinColor;


    public static CompanyAdminRes of(CompanyAdmin user) {
        CompanyAdminRes res = new CompanyAdminRes();
        res.setId(user.getId());
        res.setUserId(user.getUserId());
        res.setCompanyName(user.getCompanyName());
        res.setStartDate(user.getStartDate());
        res.setEndDate(user.getEndDate());
        res.setTel(user.getTel());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setIndustry(user.getIndustry());
        res.setSize(user.getSize());
        res.setLogoImage(user.getLogoImage());
        res.setSkinColor(user.getSkinColor());
        return res;
    }
}
