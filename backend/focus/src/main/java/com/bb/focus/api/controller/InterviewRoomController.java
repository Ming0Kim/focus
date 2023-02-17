package com.bb.focus.api.controller;

import com.bb.focus.api.service.InterviewRoomService;
import com.bb.focus.db.entity.interview.InterviewRoom;
import com.bb.focus.db.entity.interview.Room;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "면접 일정 API", tags = {"InterviewRoom"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/interviewrooms")
public class InterviewRoomController {

  @Autowired
  InterviewRoomService interviewRoomService;

  @ApiOperation(value = "대기실 코드번호 조회")
  @GetMapping("/enter/wait")
  public ResponseEntity<?> getWaitRoomCode(
      @Valid @ApiParam(value = "면접 일정 시퀀스 넘버", required = true) Long interviewRoomId) {

    Optional<InterviewRoom> interviewRoom = interviewRoomService.findById(interviewRoomId);
    Room room = interviewRoom.get().getRoom();
    String result = room.getWaitCode();

    return ResponseEntity.status(200).body(result);
  }

  @ApiOperation(value = "면접실 코드번호 조회")
  @GetMapping("/enter/real")
  public ResponseEntity<?> getRealRoomCode(
      @Valid @ApiParam(value = "면접 일정 시퀀스 넘버", required = true) Long interviewRoomId) {

    Optional<InterviewRoom> interviewRoom = interviewRoomService.findById(interviewRoomId);
    Room room = interviewRoom.get().getRoom();
    String result = room.getRealCode();

    return ResponseEntity.status(200).body(result);
  }

}
