package com.example.demo.service;

import com.example.demo.controller.dto.LibroRequestDTO;
import com.example.demo.model.Libro;
import com.example.demo.repository.LibroRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepo libroRepository;

    @Transactional
    public Libro crearLibro(LibroRequestDTO dto) {
        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setEjemplaresDisponibles(dto.getEjemplaresDisponibles());
        return libroRepository.save(libro);
    }
}