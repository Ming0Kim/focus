package com.bb.focus.api.service;

import com.bb.focus.api.response.InterviewRoomInfoRes;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.api.request.InterviewReq;
import java.util.List;

public interface InterviewService {

  Interview findInterviewById(Long interviewId);

  void createInterview(Long processId, InterviewReq interviewReq);

  List<InterviewRoomInfoRes> findInterviewRoomIds(Long processId);

}
