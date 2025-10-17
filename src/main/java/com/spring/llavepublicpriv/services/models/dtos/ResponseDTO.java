package com.spring.llavepublicpriv.services.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ResponseDTO {

    private int numOfErrors;

    private HashMap<String,String> message=new HashMap<>();
}
