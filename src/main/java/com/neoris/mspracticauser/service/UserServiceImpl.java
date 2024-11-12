package com.neoris.mspracticauser.service;

import com.neoris.mspracticauser.model.Role;
import com.neoris.mspracticauser.model.Users;
import com.neoris.mspracticauser.repositorio.RoleRepositorio;
import com.neoris.mspracticauser.repositorio.UserRepositorio;
import com.neoris.mspracticauser.util.Rol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final String USER_NOTFOUND = "Usuario no encontrado con el ID: ";


    @Autowired
    private UserRepositorio userRepositorio;
    @Autowired
    private RoleRepositorio roleRepositorio;

    @Override
    public List<Users> findAll() {
        return userRepositorio.findAll();
    }
    @Override
    public Users create(Users user) {
        user.setRoles(getRolesDefualt(user));
        return userRepositorio.save(user);
    }

    @Override
    public Users update(Long id, Users newModel) {

        logger.debug("Buscando usuario con ID: \"{}\" para actualizar", id);
        Optional<Users> userOptional = userRepositorio.findById(id);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setId(id);
            user.setName(newModel.getName());
            user.setEmail(newModel.getEmail());
            user.setStatus(newModel.getStatus());
            user.setRoles(getRolesDefualt(user));
            user.setPassword(newModel.getPassword());


            return userRepositorio.save(user);
        }
        logger.warn(USER_NOTFOUND, id);
        throw new RuntimeException(USER_NOTFOUND + id);

    }

    private Set<Role> getRolesDefualt(Users user){

        Set<Role> roles = user.getRoles();
        System.out.println("roles roles.size()"+ roles.size());
        if(roles.size() == 0){
            Role role = new Role();
            role.setName("USER");
            Set<Role> rollesExistentes = roleRepositorio.findByName("USER");

            System.out.println("roles name USER roles.size()"+ roles.size());
            if(rollesExistentes.size() == 0){
                System.out.println("creamos el roll USER");
                roleRepositorio.save(role);
                roles.add(role);
            }
            roles = rollesExistentes;
        }
        return roles;
    }


    @Override
    public void delete(Long id) {
        logger.debug("Buscando usuario con ID: \"{}\" para eliminar", id);
        if (userRepositorio.existsById(id)) {
            userRepositorio.deleteById(id);
        } else {
            logger.warn(USER_NOTFOUND, id);
            throw new RuntimeException(USER_NOTFOUND + id);
        }
    }

    @Override
    public Optional<Users> get(Long id) {
        logger.debug("Buscando usuario con ID: \"{}\"", id);
        return userRepositorio.findById(id);
    }

    public Page<Users> getUsers(String name,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (name != null && !name.isEmpty()) {
            logger.debug("Buscando usuarios por name: \"{}\", paguina {} size {}", name,page,size);
            return userRepositorio.findByNameContainingIgnoreCase(name, pageable);
        } else {
            logger.debug("Buscando usuarios  paguina {} size {}", page,size);
            return userRepositorio.findAll(pageable);
        }

    }

    @Override
    public Users getForEmail(String email) {
        logger.debug("Buscando usuario con email: \"{}\"", email);
        return userRepositorio.findByEmail(email);
    }

    @Override
    public List<Users> getUserAdmin(){

       Set<Role> list =  roleRepositorio.findByName("ADMIN");

       return userRepositorio.findByRoles(list);

    }



}
