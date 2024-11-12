package com.neoris.mspracticauser.repositorio;

import com.neoris.mspracticauser.model.Role;
import com.neoris.mspracticauser.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface  UserRepositorio extends JpaRepository<Users, Long> {

    Page<Users> findByNameContainingIgnoreCase(String name,Pageable pageable);

    Users findByEmail(String email);

    List<Users> findByRoles(Set<Role> roleSet);
}
