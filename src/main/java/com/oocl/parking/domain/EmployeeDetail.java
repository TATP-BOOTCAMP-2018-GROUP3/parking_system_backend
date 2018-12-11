package com.oocl.parking.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class EmployeeDetail implements UserDetails {
    private final Long id;
    private final String accountName;
    private final String hashedPassword;
    private final String email;
    private final GrantedAuthority role;

    public EmployeeDetail(Long id, String accountName, String hashedPassword, String email, GrantedAuthority role){
        this.id = id;
        this.accountName = accountName;
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(role);
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return accountName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
