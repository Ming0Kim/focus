package com.bb.focus.db.repository;

public interface ApplicantInterviewRoomCustomRepository {

  void deleteByInterviewRoomIdAndApplicantId(Long interviewRoomId, Long applicantId);

}
