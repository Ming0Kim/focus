package com.bb.focus.db.entity.applicant.school;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "applicants_graduates")
public class ApplicantGraduate {

    @Id
    @GeneratedValue
    @Column(name = "applicant_graduate_id")
    private Long id;

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    @Column(length = 10)
    private String area;
}
