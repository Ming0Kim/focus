package com.bb.focus.api.service;

import com.bb.focus.api.request.ServiceAdminRegisterPostReq;
import com.bb.focus.db.entity.admin.ServiceAdmin;
import com.bb.focus.db.repository.ServiceAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("serviceAdminService")
public class ServiceAdminServiceImpl implements ServiceAdminService {

  @Autowired
  ServiceAdminRepository serviceAdminRepository;

//  @Autowired
//  PasswordEncoder passwordEncoder;

  @Override
  public ServiceAdmin createUser(ServiceAdminRegisterPostReq serviceAdminRegisterInfo) {
    ServiceAdmin serviceAdmin = new ServiceAdmin();
    serviceAdmin.setUserId(serviceAdminRegisterInfo.getUserId());
//    serviceAdmin.setPwd(EncryptionUtils.encryptSHA256(serviceAdminRegisterInfo.getPassword()));
    serviceAdmin.setPwd(serviceAdminRegisterInfo.getPassword());
    serviceAdmin.setEmail(serviceAdminRegisterInfo.getEmail());
    serviceAdmin.setTel(serviceAdminRegisterInfo.getTel());
    serviceAdmin.setName(serviceAdminRegisterInfo.getName());
    serviceAdmin.setUserRole(serviceAdminRegisterInfo.getUserRole());
    return serviceAdminRepository.save(serviceAdmin);
  }

  @Override
  public ServiceAdmin getServiceAdminByUserId(String userId) {
    ServiceAdmin serviceAdmin = serviceAdminRepository.findServiceAdminByUserId(userId);
    return serviceAdmin;
  }

  @Override
  public ServiceAdmin getServiceAdminById(Long id) {
    ServiceAdmin serviceAdmin = serviceAdminRepository.findServiceAdminById(id);
    return serviceAdmin;
  }
}
