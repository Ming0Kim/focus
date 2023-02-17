package com.bb.focus.api.controller;

import com.bb.focus.api.request.RoomReq;
import com.bb.focus.api.service.RoomService;
import com.bb.focus.common.model.response.BaseResponseBody;
import com.bb.focus.db.entity.interview.Room;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "룸 API", tags = {"Room"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/rooms")
public class RoomController {

  private final RoomService roomService;

  @ApiOperation(value = "룸 생성", notes = "기업관리자로부터 입력받은 정보로 룸을 생성한다.")
  @PostMapping("/evaluators/{interview-id}")
  public ResponseEntity<?> createRoom(
      @RequestBody @Valid @ApiParam(value = "룸 생성 정보", required = true) RoomReq.Create roomInfoList) {

    Room room = roomService.createRoom(roomInfoList);
    roomService.updateRoomById(room); // 면접실 코드, 대기실 코드 바로 넣어주기

    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
  }

}
