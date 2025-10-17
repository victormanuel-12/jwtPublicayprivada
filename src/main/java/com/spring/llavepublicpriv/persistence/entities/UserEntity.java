package com.spring.llavepublicpriv.persistence.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_Id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long userId;

    @Column(name="first_Name", nullable = false)
    private String firstName;

    @Column(name="last_Name", nullable = false)
    private String lastName;

    @Column(name="email", unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY  // ✅ Cambiar a EAGER para evitar LazyInitializationException
    )
    @JoinTable(
            name = "User_Rol",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idRol")
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<RolEntity> rol = new ArrayList<>();
}