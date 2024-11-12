package com.neoris.mspracticauser.service;

import com.neoris.mspracticauser.model.Role;
import com.neoris.mspracticauser.model.Users;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface  UserService {

    public List<Users> findAll();
    public Users create(Users user);
    public Users update(Long id, Users user);
    public void delete(Long id);
    public Optional<Users> get(Long id);
    public Page<Users> getUsers(String name,int page, int size);
    public Users getForEmail(String email);
    public List<Users> getUserAdmin();

}
