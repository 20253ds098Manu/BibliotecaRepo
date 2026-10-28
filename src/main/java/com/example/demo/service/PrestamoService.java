package com.example.demo.service;

import com.example.demo.Exceptions.BadRequestException;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.controller.dto.PrestamoRequestDTO;
import com.example.demo.model.EstadoPrestamo;
import com.example.demo.model.Lector;
import com.example.demo.model.Libro;
import com.example.demo.model.Prestamo;
import com.example.demo.repository.LectorRepo;
import com.example.demo.repository.LibroRepo;
import com.example.demo.repository.PrestamoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestamoService {

    private final PrestamoRepo prestamoRepository;
    private final LectorRepo lectorRepository;
    private final LibroRepo libroRepository;

    @Transactional
    public Prestamo solicitarPrestamo(PrestamoRequestDTO dto) {

        Lector lector = lectorRepository.findById(dto.getLectorId())
                .orElseThrow(() -> new ResourceNotFoundException("El lector con ID " + dto.getLectorId() + " no existe"));

        if (!lector.isActivo()) {
            throw new BadRequestException("El lector está bloqueado y no puede solicitar préstamos");
        }

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new ResourceNotFoundException("El libro con ID " + dto.getLibroId() + " no existe"));

        if (libro.getEjemplaresDisponibles() < 1) {
            throw new BadRequestException("No hay ejemplares disponibles de este libro");
        }
        int limite = lector.isMembresiaPremium() ? 5 : 3;

        long activos = prestamoRepository.countByLectorIdAndEstadoIn(
                lector.getId(), List.of(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO));

        if (activos >= limite) {
            throw new BadRequestException("El lector alcanzó su límite de " + limite + " préstamos activos");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionMaxima(LocalDate.now().plusDays(14));
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolverLibro(Long id) {

        Prestamo prestamo = buscarPrestamo(id);

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new BadRequestException("Este préstamo ya fue devuelto");
        }

        boolean entregaTardia = LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima());

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        if (entregaTardia) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);
        }

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo renovarPrestamo(Long id) {

        Prestamo prestamo = buscarPrestamo(id);

        if (prestamo.getEstado() == EstadoPrestamo.VENCIDO
                || prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new BadRequestException("Un préstamo " + prestamo.getEstado() + " no se puede renovar");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));

        return prestamoRepository.save(prestamo);
    }
    private Prestamo buscarPrestamo(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El préstamo con ID " + id + " no existe"));
    }
}