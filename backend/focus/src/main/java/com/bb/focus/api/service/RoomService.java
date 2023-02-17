package com.bb.focus.api.service;

import com.bb.focus.api.request.RoomReq;
import com.bb.focus.db.entity.interview.Room;

public interface RoomService {

  Room createRoom(RoomReq.Create roomInfo);

  Long updateRoomById(Room room);

  Room autoCreateRoom(Long interviewId);
}
