package com.spring.llavepublicpriv.services.models.validation;

import com.spring.llavepublicpriv.persistence.entities.UserEntity;
import com.spring.llavepublicpriv.services.models.dtos.ResponseDTO;

public class UserValidation {

    public ResponseDTO validate(UserEntity user){
        ResponseDTO response=new ResponseDTO();
        response.setNumOfErrors(0);
        if(user.getFirstName()==null ||user.getFirstName().length()<3 || user.getFirstName().length()>15){
            response.setNumOfErrors(response.getNumOfErrors()+1);
            response.getMessage().put("firstName","El campo nombre debe tener entre 3 y max 15 caracteres");
        }
        if(user.getLastName()==null||user.getLastName().length()<3||user.getLastName().length()>15){
            response.setNumOfErrors(response.getNumOfErrors()+1);
            response.getMessage().put("lastName","El apellido debe tener entre 3 y 15 caracteres maximo");
        }
        if(user.getEmail()==null||!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            response.setNumOfErrors(response.getNumOfErrors()+1);
            response.getMessage().put("email","El campo email no es valido");
        }
        if(user.getPassword()==null || user.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")){
            response.setNumOfErrors(response.getNumOfErrors()+1);
            response.getMessage().put("password","La contraseña debe tener entre 8 y 16 caracteres,al menos un numero ,una minuscula y una mayuscula");
        }
        return response;
    }
}
