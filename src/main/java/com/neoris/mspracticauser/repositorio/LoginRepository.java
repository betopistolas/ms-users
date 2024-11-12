package com.neoris.mspracticauser.repositorio;

import com.neoris.mspracticauser.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository  extends JpaRepository<Users, Long> {

}