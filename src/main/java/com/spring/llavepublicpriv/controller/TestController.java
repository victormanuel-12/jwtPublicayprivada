package com.spring.llavepublicpriv.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("Acceso permitido: ADMIN");
    }

    @GetMapping("/medico")
    @PreAuthorize("hasRole('MEDICO')")
    public ResponseEntity<String> medicoEndpoint() {
        return ResponseEntity.ok("Acceso permitido: MEDICO");
    }

    @GetMapping("/paciente")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<String> pacienteEndpoint() {
        return ResponseEntity.ok("Acceso permitido: PACIENTE");
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<String> allRolesEndpoint() {
        return ResponseEntity.ok("Acceso permitido: Todos los roles");
    }
}