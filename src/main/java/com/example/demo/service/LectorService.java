package com.example.demo.service;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.controller.dto.LectorRequestDTO;
import com.example.demo.model.EstadoPrestamo;
import com.example.demo.model.Lector;
import com.example.demo.model.Prestamo;
import com.example.demo.repository.LectorRepo;
import com.example.demo.repository.PrestamoRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LectorService {

    private final LectorRepo lectorRepository;
    private final PrestamoRepo prestamoRepository;

    @Transactional
    public Lector crearLector(LectorRequestDTO dto) {
        Lector lector = new Lector();
        lector.setNombre(dto.getNombre());
        lector.setActivo(true);
        lector.setMembresiaPremium(Boolean.TRUE.equals(dto.getMembresiaPremium()));
        return lectorRepository.save(lector);
    }

    @Transactional
    public Lector rehabilitarLector(Long id) {
        Lector lector = lectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El lector con ID " + id + " no existe"));

        lector.setActivo(true);
        return lectorRepository.save(lector);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamosActivosLector(Long id) {
        if (!lectorRepository.existsById(id)) {
            throw new ResourceNotFoundException("El lector con ID " + id + " no existe");
        }
        return prestamoRepository.findByLectorIdAndEstadoIn(
                id, List.of(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO));
    }
}