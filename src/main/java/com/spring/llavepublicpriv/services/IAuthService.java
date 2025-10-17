package com.spring.llavepublicpriv.services;

import com.spring.llavepublicpriv.persistence.entities.UserEntity;
import com.spring.llavepublicpriv.services.models.dtos.LoginDTO;
import com.spring.llavepublicpriv.services.models.dtos.ResponseDTO;

import java.util.HashMap;

public interface IAuthService {
     HashMap<String,String> login(LoginDTO loginDTO) throws Exception;
    ResponseDTO register(UserEntity user) throws Exception;
}
