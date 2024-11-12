package com.neoris.mspracticauser.controller;

import com.neoris.mspracticauser.model.UserNotFoundException;
import com.neoris.mspracticauser.model.Users;
import com.neoris.mspracticauser.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;



@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Tag(name = "get", description = "GET All Users methods of APIs")
    @GetMapping("/all")
    public ResponseEntity<List<Users>> listar() {
        log.info("Petición para obtener todos los usuarios");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @Tag(name = "POST", description = "Petición para crear un nuevo usuario")
    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users model, HttpServletRequest request) {
        log.info("Petición para crear un nuevo usuario: {}", model);
        try {
            Users newModel = userService.create(model);
            log.debug("Usuario creado exitosamente: {}", newModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(newModel);
        } catch (RuntimeException e) {
            log.error("Error al crear el usuario: {}", model, e);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();  // 409 Conflict
        }
    }

    @Tag(name = "GET", description = "Petición para obtener un solo usuario")
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUsuarioById(@PathVariable Long id) {
        log.info("Petición para obtener el usuario con ID: {}", id);
        Users usuario = userService.get(id).orElseThrow(() ->
                new UserNotFoundException("Usuario no encontrado con el ID: " + id));
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @Tag(name = "PUT", description = "Petición para actualizar usuario")
    @PutMapping( "/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users newModel) {

        log.info("Petición para actualizar el usuario con ID: {}", id);
        log.info(newModel);
        try {
            newModel = userService.update(id, newModel);
            log.debug("Usuario actualizado exitosamente: {}", newModel);
            return new ResponseEntity<>(newModel, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al obtener el usuario con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @Tag(name = "DELETE", description = "Petición para eliminar un usuario")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        log.info("Petición para eliminar el usuario con ID: {}", id);
        try {
            userService.delete(id);
            log.debug("Usuario eliminado exitosamente: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error al obtener el usuario con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Tag(name = "GET", description = "Petición para consultar usuarios y buscarlos por nombre")
    @GetMapping
    public ResponseEntity<Page<Users>> getUsuariosPaginadosConFiltro(
            @RequestParam(required = false) String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Petición para consultar los usuarios por filtros");
        Page<Users> usuarios = userService.getUsers(nombre, page, size);
        return ResponseEntity.ok(usuarios);
    }

}
