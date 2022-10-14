package com.geopagos.toumament.service.security;

import com.geopagos.toumament.dto.UserPrincipalDTO;
import com.geopagos.toumament.errorhandler.GeopagosToumamentGenericClientException;
import com.geopagos.toumament.util.GeopagosToumamentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeopagosToumamentDetailService implements UserDetailsService{
    final GeopagosToumamentUtil pignoraticioUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(Boolean.TRUE.equals(this.pignoraticioUtil.validateCodigoUsuario(username))) {
                      List<GrantedAuthority> authorities = null;
            return new UserPrincipalDTO(username, username, "", "seda",
                    true, authorities, null);
        }else {
            throw new GeopagosToumamentGenericClientException("Error document DNI", HttpStatus.BAD_REQUEST);
        }
    }



}
