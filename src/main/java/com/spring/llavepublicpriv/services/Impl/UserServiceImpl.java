package com.spring.llavepublicpriv.services.Impl;

import com.spring.llavepublicpriv.persistence.entities.UserEntity;
import com.spring.llavepublicpriv.persistence.repositories.UserRepository;
import com.spring.llavepublicpriv.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private  final UserRepository userRepository;

    public List<UserEntity> findAllUser(){
        return userRepository.findAll();
    }
}
