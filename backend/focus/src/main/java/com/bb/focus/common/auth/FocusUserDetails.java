package com.bb.focus.common.auth;

import com.bb.focus.db.entity.admin.ServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 부가 상세정보(활성화 여부, 만료, 롤 등) 정의.
 */
public class FocusUserDetails implements UserDetails {
    @Autowired
    ServiceAdmin serviceAdmin;
    boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialNonExpired;
    boolean enabled = false;
    List<GrantedAuthority> roles = new ArrayList<>();

    public FocusUserDetails(ServiceAdmin serviceAdmin) {
        super();
        this.serviceAdmin = serviceAdmin;
    }

    public ServiceAdmin getUser() {
        return this.serviceAdmin;
    }

    @Override
    public String getPassword() {
        return this.serviceAdmin.getPwd();
    }

    @Override
    public String getUsername() {
        return this.serviceAdmin.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    public void setAuthorities(List<GrantedAuthority> roles) {
        this.roles = roles;
    }
}
