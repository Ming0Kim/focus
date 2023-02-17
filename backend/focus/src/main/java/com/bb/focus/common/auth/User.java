package com.bb.focus.common.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
  private Long id;

  private String userId;
  private String pwd;
  private Byte userRole;

}
