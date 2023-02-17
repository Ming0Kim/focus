package com.bb.focus.api.service;

import com.bb.focus.api.request.ServiceAdminRegisterPostReq;
import com.bb.focus.db.entity.admin.ServiceAdmin;

public interface ServiceAdminService {

    ServiceAdmin createUser(ServiceAdminRegisterPostReq serviceAdminRegisterInfo);

    ServiceAdmin getServiceAdminByUserId(String userId);
    ServiceAdmin getServiceAdminById(Long id);

}
