package com.bb.focus.db.entity.company;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_messages")
public class ChatMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="chat_message_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="chat_room_id")
  private Chat chat;

  @NotNull
  @Column(length = 500)
  private String message;

  @NotNull
  private LocalDateTime createdAt;



}
