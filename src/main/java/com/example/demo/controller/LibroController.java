package com.example.demo.controller;

import com.example.demo.controller.dto.LibroRequestDTO;
import com.example.demo.model.Libro;
import com.example.demo.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libro")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    @PostMapping
    public ResponseEntity<Libro> crear(@Valid @RequestBody LibroRequestDTO dto) {
        return new ResponseEntity<>(libroService.crearLibro(dto), HttpStatus.CREATED);
    }
}
