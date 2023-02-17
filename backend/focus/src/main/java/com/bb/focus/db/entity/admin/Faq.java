package com.bb.focus.db.entity.admin;

import com.sun.istack.NotNull;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "faqs")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_admin_id")
    private ServiceAdmin serviceAdmin;

    @NotNull
    @Column(length = 300)
    private String title;

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull
    @Column(length = 5000)
    private String content;

    @NotNull
    @ColumnDefault("3")
    private Byte authRange;  //3:기업관리자,평가자 열람 가능, 4:기업관리자,지원자 열람 가능


}
