package com.spring.llavepublicpriv.controller;


import com.spring.llavepublicpriv.persistence.entities.UserEntity;
import com.spring.llavepublicpriv.services.IAuthService;
import com.spring.llavepublicpriv.services.models.dtos.LoginDTO;
import com.spring.llavepublicpriv.services.models.dtos.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String,String>> login(@RequestBody LoginDTO loginRequest) throws Exception {
        HashMap<String,String> login=authService.login(loginRequest);
        if(login.containsKey("jwt")){
            return new ResponseEntity<>(login,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(login,HttpStatus.UNAUTHORIZED);
        }
    }
}
