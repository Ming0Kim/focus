package com.bb.focus.db.repository;

import com.bb.focus.db.entity.interview.InterviewRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRoomRepository extends JpaRepository<InterviewRoom, Long>,
    InterviewRoomCustomRepository {

  List<InterviewRoom> findByInterviewId(Long interviewId);
  List<InterviewRoom> findByProcessNameAndInterviewRound(String processName,Long CurrentStep);
}
