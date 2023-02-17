package com.bb.focus.db.entity.admin;

import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "service_admins")
public class ServiceAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "service_admin_id")
    private Long id;

    @NotNull
    @Column(length = 20)
    private String userId;

    @NotNull
    @Column(length = 64)
    private String pwd;

    @NotNull
    @Column(length = 10)
    private String name;

    @NotNull
    @Column(length = 13)
    private String tel;

    @NotNull
    @Column(length = 45)
    private String email;

    @NotNull
    @ColumnDefault("1")
    private Byte userRole;   //1:서비스관리자, 2:기업관리자, 3:평가자, 4:지원자

    @OneToMany(targetEntity = com.bb.focus.db.entity.admin.Faq.class, mappedBy = "serviceAdmin")
    private List<Faq> faqList = new ArrayList<>();

    @OneToMany(targetEntity = com.bb.focus.db.entity.admin.ServiceNotice.class, mappedBy = "serviceAdmin")
    private List<ServiceNotice> serviceNoticeList = new ArrayList<>();
}
