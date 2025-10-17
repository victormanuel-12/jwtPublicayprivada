package com.spring.llavepublicpriv.persistence.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name="Rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_rol", nullable = false, unique = true)
    private String nameRol;  // ✅ Eliminadas las anotaciones @JsonProperty incorrectas
}