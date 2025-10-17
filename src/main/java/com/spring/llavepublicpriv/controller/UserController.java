package com.spring.llavepublicpriv.controller;

import com.spring.llavepublicpriv.persistence.entities.UserEntity;
import com.spring.llavepublicpriv.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/findAll")
    public ResponseEntity<List<UserEntity>> userAll(){
        return new ResponseEntity<>(userService.findAllUser(), HttpStatus.OK);
    }
}
