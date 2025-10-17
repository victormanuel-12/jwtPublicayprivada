package com.spring.llavepublicpriv.services.Impl;

import com.spring.llavepublicpriv.persistence.entities.RolEntity;
import com.spring.llavepublicpriv.persistence.entities.UserEntity;
import com.spring.llavepublicpriv.persistence.repositories.IRolRepository;
import com.spring.llavepublicpriv.persistence.repositories.UserRepository;
import com.spring.llavepublicpriv.services.IAuthService;
import com.spring.llavepublicpriv.services.IJWTUtilityService;
import com.spring.llavepublicpriv.services.models.dtos.LoginDTO;
import com.spring.llavepublicpriv.services.models.dtos.ResponseDTO;
import com.spring.llavepublicpriv.services.models.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IJWTUtilityService jwtUtilityService;
    private final UserValidation userValidation;
    private final UserRepository userRepository;
    private final IRolRepository rolRepository;

    @Override
    public HashMap<String,String> login(LoginDTO loginDTO) throws Exception{
        try{
            HashMap<String,String> jwt=new HashMap<>();
            Optional<UserEntity> userEntity=userRepository.findByEmail(loginDTO.getEmail());
            if(userEntity.isEmpty()){
                jwt.put("error","Usuario no registrado");
                return jwt;
            }
            if(verifyPassword(loginDTO.getPassword(),userEntity.get().getPassword())){
                List<String> role=userEntity.get().getRol().stream()
                        .map(RolEntity::getNameRol)
                        .toList();
                jwt.put("jwt", jwtUtilityService.generateJWT(userEntity.get().getUserId(),role));
            }else{
                jwt.put("error","Authentication fallida");
            }
            return jwt;
        }catch (Exception e){
            throw new Exception(e.toString());
        }
    }

    public ResponseDTO register(UserEntity user) throws Exception {
        try {
            // 1️⃣ Validar usuario
            ResponseDTO response = userValidation.validate(user);
            if (response.getNumOfErrors() > 0) {
                return response;
            }

            // 2️⃣ Verificar si ya existe usuario con el mismo email
            boolean userExists = userRepository.findAll()
                    .stream()
                    .anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));

            if (userExists) {
                response.getMessage().put("error", "El usuario ya existe con este correo");
                response.setNumOfErrors(1);
                return response;
            }
                BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
            user.setPassword(encoder.encode((user.getPassword())));
            Optional<RolEntity> rol=rolRepository.findById(1L);
            user.getRol().add(rol.get());
            userRepository.save(user);
            response.getMessage().put("message", "Usuario registrado correctamente");
            return response;

        } catch (Exception e) {
            throw new Exception("Error al registrar usuario: " + e.getMessage(), e);
        }
    }


    private boolean verifyPassword(String enteredPassword,String storedPassword){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword,storedPassword);
    }
}
