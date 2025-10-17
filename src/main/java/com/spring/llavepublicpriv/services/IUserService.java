package com.spring.llavepublicpriv.services;

import com.spring.llavepublicpriv.persistence.entities.UserEntity;

import java.util.List;

public interface IUserService {
    List<UserEntity> findAllUser();
}
