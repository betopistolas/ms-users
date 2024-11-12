package com.neoris.mspracticauser.controller;


import com.neoris.mspracticauser.model.ErrorRes;
import com.neoris.mspracticauser.model.LoginRequest;
import com.neoris.mspracticauser.model.LoginResponse;
import com.neoris.mspracticauser.model.Users;
import com.neoris.mspracticauser.service.UserService;
import com.neoris.mspracticauser.util.JwtUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/rest/auth")
public class LoginController {


    private static final Logger log = LogManager.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager  authenticationManager;
    @Autowired
    private UserService userService;

    private JwtUtil jwtUtil;

    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginRequest loginReq) {

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));

            Authentication authentication = new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String email = authentication.getName();
            Users model = userService.getForEmail(email);

            System.out.println("asda");
            //if(model.getPassword().equals(loginReq.getPassword())) {
                String token = jwtUtil.createToken(model);
                LoginResponse loginRes = new LoginResponse(email, token);

                return ResponseEntity.ok(loginRes);

        } catch (BadCredentialsException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
