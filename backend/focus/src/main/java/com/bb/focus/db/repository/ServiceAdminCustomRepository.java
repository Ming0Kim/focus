package com.bb.focus.db.repository;

import com.bb.focus.db.entity.admin.ServiceAdmin;
import java.util.Optional;

public interface ServiceAdminCustomRepository {

  ServiceAdmin findServiceAdminByUserId(String userId);
  ServiceAdmin findServiceAdminById(Long id);

}
