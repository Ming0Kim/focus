package com.bb.focus.db.repository;

import com.bb.focus.db.entity.helper.ApplicantInterviewRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantInterviewRoomRepository extends JpaRepository<ApplicantInterviewRoom, Long>, ApplicantInterviewRoomCustomRepository {

}
