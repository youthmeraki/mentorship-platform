package com.youthmeraki.mentorshipplatform.models;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        RoleType roleName = user.getRole().getName();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName.toString());
        return Collections.singleton(authority);
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public boolean isApproved() {
        if (user.getRole().getName() == RoleType.ROLE_MENTOR || user.getRole().getName() == RoleType.ROLE_ADMIN) {
            return true;
        }
        return user.getMentee().isApproved();
    }

    public boolean isPaid() {
        if (user.getRole().getName() == RoleType.ROLE_MENTOR || user.getRole().getName() == RoleType.ROLE_ADMIN) {
            return true;
        }
        return user.getMentee().isPaid();
    }
}
