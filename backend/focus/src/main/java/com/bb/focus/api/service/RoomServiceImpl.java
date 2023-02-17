package com.bb.focus.api.service;

import com.bb.focus.api.request.RoomReq;
import com.bb.focus.common.util.EncryptionUtils;
import com.bb.focus.db.entity.interview.Interview;
import com.bb.focus.db.entity.interview.Room;
import com.bb.focus.db.repository.InterviewRepository;
import com.bb.focus.db.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roomService")
public class RoomServiceImpl implements RoomService {

  @Autowired
  InterviewService interviewService;

  @Autowired
  RoomRepository roomRepository;
  @Autowired
  private InterviewRepository interviewRepository;

  @Override
  public Room createRoom(RoomReq.Create roomInfo) {
    Room room = new Room();
    return roomRepository.save(room);
  }

  @Override
  public Long updateRoomById(Room room) {
    String newRealCode = EncryptionUtils.encryptSHA256("real" + room.getId());
    String newWaitCode = EncryptionUtils.encryptSHA256("wait" + room.getId());
    roomRepository.save(Room.builder()
        .id(room.getId())
        .realCode(newRealCode)
        .waitCode(newWaitCode).build());
    return room.getId();
  }

  @Override
  public Room autoCreateRoom(Long interviewId) {
    Room room = new Room();
    Interview interview = interviewRepository.findById(interviewId)
        .orElseThrow(IllegalArgumentException::new);
    return roomRepository.save(room);
  }
}
