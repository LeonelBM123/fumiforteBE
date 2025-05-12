/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.models;

import jakarta.persistence.Entity;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author PC
 */

@AllArgsConstructor
@NoArgsConstructor
public class SecurityUser implements UserDetails{

    private Usuario user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRol()));
    }

    @Override
    public String getPassword() {
        return user.getContraseña();
    }

    @Override
    public String getUsername() {
        return user.getNombreCompleto();
    }
    
}
