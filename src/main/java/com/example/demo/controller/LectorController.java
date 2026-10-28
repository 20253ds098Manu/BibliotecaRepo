package com.example.demo.controller;

import com.example.demo.controller.dto.LectorRequestDTO;
import com.example.demo.model.Lector;
import com.example.demo.model.Prestamo;
import com.example.demo.service.LectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lector")
@RequiredArgsConstructor
public class LectorController {

    private final LectorService lectorService;

    @PostMapping
    public ResponseEntity<Lector> crear(@Valid @RequestBody LectorRequestDTO dto) {
        return new ResponseEntity<>(lectorService.crearLector(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/rehabilitar")
    public ResponseEntity<Lector> rehabilitar(@PathVariable Long id) {
        return ResponseEntity.ok(lectorService.rehabilitarLector(id));
    }

    @GetMapping("/{id}/prestamos-activos")
    public ResponseEntity<List<Prestamo>> prestamosActivos(@PathVariable Long id) {
        return ResponseEntity.ok(lectorService.listarPrestamosActivosLector(id));
    }
}
