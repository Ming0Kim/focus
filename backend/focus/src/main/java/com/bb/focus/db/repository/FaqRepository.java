package com.bb.focus.db.repository;

import com.bb.focus.db.entity.admin.Faq;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long>,
    FaqCustomRepository {

  Page<Faq> findAllByOrderByCreatedAtDesc(Pageable pageable);

  Page<Faq> findByAuthRangeOrderByCreatedAtDesc(Pageable pageable, Byte userRole);

  Optional<Faq> findById(Long id);

}
