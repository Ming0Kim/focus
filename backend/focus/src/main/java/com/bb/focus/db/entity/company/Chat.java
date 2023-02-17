package com.bb.focus.db.entity.company;

import com.bb.focus.db.entity.applicant.Applicant;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="chat_room")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_admin_id")
    private CompanyAdmin companyAdmin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="applicant_id")
    private Applicant applicant;

    @OneToMany(targetEntity = com.bb.focus.db.entity.company.ChatMessage.class, mappedBy = "chat")
    private List<ChatMessage> chatMessageList = new ArrayList<>();



}
