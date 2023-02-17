package com.bb.focus.api.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@ApiModel("SchoolDto")
public class SchoolDto {
    @ApiModelProperty(name="학교이름", example="서울대학교")
    String name;
    // 세종특별자치구 때문에 글자수를 늘려야함.

    @ApiModelProperty(name="분교", example = "국제캠퍼스")
    String Campus;

    @ApiModelProperty(name="학교지역" ,example = "서울특별시")
    String area;


}
