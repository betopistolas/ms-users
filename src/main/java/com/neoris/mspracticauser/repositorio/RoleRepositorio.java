package com.neoris.mspracticauser.repositorio;

import com.neoris.mspracticauser.model.Role;
import com.neoris.mspracticauser.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface RoleRepositorio extends JpaRepository<Role, Long> {
    Set<Role> findByName(String name);
}
