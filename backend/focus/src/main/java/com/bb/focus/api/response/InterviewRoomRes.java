package com.bb.focus.api.response;

import com.bb.focus.db.entity.helper.ApplicantEvaluator;
import com.bb.focus.db.entity.interview.InterviewRoom;
import com.bb.focus.db.entity.interview.Room;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EvaluatorRes")
@NoArgsConstructor
public class InterviewRoomRes implements Comparable<InterviewRoomRes> {

  @ApiModelProperty(name = "면접 일정 시퀀스 넘버", example = "1")
  private Long id;
  @ApiModelProperty(name = "해당 면접실이 속한 전형 이름", example = "삼성 하반기 IT 공채")
  private String processName;
  @ApiModelProperty(name = "해당 면접실이 속한 면접의 차수", example = "1")
  private int interviewRound;
  @ApiModelProperty(name = "면접 일정 이름", example = "홍길동")
  private String name;

  @ApiModelProperty(name = "면접 일정 시작 시간", example = "1111")
  private LocalDateTime startTime;

  @ApiModelProperty(name = "면접 일정 종료 시간", example = "개발부")
  private LocalDateTime endTime;

  @ApiModelProperty(name = "면접 날짜")
  private LocalDate date;

  @ApiModelProperty(name = "면접 일정 걸리는 시간(분)", example = "30")
  private int duration;

  @ApiModelProperty(name = "룸 정보")
  private String realRoomCode;


  public InterviewRoomRes(InterviewRoom interviewRoom) {
    id = interviewRoom.getId();
    name = interviewRoom.getName();
    startTime = interviewRoom.getStartTime();
    endTime = interviewRoom.getEndTime();
    duration = interviewRoom.getDuration();
    date = interviewRoom.getDate();
    processName = interviewRoom.getProcessName();
    interviewRound = interviewRoom.getInterviewRound();
    realRoomCode = interviewRoom.getRoom().getRealCode();
  }

  public InterviewRoomRes of(InterviewRoom interviewRoom){
    InterviewRoomRes res = new InterviewRoomRes();
    res.setId(interviewRoom.getId());
    res.setName(interviewRoom.getName());
    res.setStartTime(interviewRoom.getStartTime());
    res.setEndTime(interviewRoom.getEndTime());
    res.setDuration(interviewRoom.getDuration());
    res.setDate(interviewRoom.getDate());
    res.setProcessName(interviewRoom.getProcessName());
    res.setInterviewRound(interviewRoom.getInterviewRound());
    return res;
  }

  public InterviewRoomRes(Long id, String name, LocalDateTime startTime, LocalDateTime endTime,
      int duration, LocalDate date, int interviewRound, String processName, String realRoomCode) {
    this.id = id;
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.duration = duration;
    this.date = date;
    this.interviewRound = interviewRound;
    this.processName = processName;
    this.realRoomCode = realRoomCode;
  }

  @Override
  public int compareTo(InterviewRoomRes o) {
    return this.startTime.compareTo(o.startTime);
  }

}
