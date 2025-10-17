package com.spring.llavepublicpriv.persistence.repositories;

import com.spring.llavepublicpriv.persistence.entities.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepository extends JpaRepository<RolEntity,Long> {

}
