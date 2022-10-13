package com.geopagos.toumament.dto;

import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserPrincipalDTO  implements UserDetails{

    private String id;
    private String userType;
    private String password;
    private String name;
    private boolean status;
    private Collection<? extends GrantedAuthority> authorities;
    private Date lastPasswordResetDate;

    public UserPrincipalDTO(String id, String userType, String password, String name,
                            boolean status, Collection<? extends GrantedAuthority> authorities,
                            Date lastPasswordResetDate){
        this.id=id;
        this.userType=userType;
        this.password=password;
        this.name=name;
        this.status=status;
        this.authorities=authorities;
        this.lastPasswordResetDate=lastPasswordResetDate;
    }

    public String getUserType(){
        return userType;
    }

    public String getName(){
        return name;
    }

    public Date getLastPasswordResetDate(){
        return lastPasswordResetDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
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