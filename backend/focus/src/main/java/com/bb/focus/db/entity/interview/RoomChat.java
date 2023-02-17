package com.bb.focus.db.entity.interview;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "room_chats")
@Table(name = "room_chats")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_chat_id")
    private Long id;

    @NotNull
    @Column(length = 50)
    private String senderId;

    @NotNull
    @Column(length = 300)
    private String message;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "roomChat")
    private InterviewRoom interviewRoom;
}
