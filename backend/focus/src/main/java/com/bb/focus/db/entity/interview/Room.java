package com.bb.focus.db.entity.interview;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Table(name = "room")
@Builder
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "room_id")
  private Long id;

  @Column(length = 64)
  private String realCode;

  @Column(length = 64)
  private String waitCode;

  @OneToMany(targetEntity = com.bb.focus.db.entity.interview.InterviewRoom.class, mappedBy = "room")
  private List<InterviewRoom> interviewRoomList = new ArrayList<>();


}
