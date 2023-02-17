package com.bb.focus.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DecisionReq {

    private Long processId;
    private InterviewResultReq interviewResultReq;

}
