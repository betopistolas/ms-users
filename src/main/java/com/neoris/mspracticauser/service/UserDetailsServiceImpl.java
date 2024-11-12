package com.neoris.mspracticauser.service;

import com.neoris.mspracticauser.repositorio.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepositorio usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return (UserDetails) usuarioRepository.findByEmail(username);
    }
}

