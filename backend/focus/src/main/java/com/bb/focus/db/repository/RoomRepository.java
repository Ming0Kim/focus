package com.bb.focus.db.repository;

import com.bb.focus.db.entity.company.CompanyAdmin;
import com.bb.focus.db.entity.interview.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>,
    RoomCustomRepository {


}
