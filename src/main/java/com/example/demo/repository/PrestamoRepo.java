package com.example.demo.repository;

import com.example.demo.model.EstadoPrestamo;
import com.example.demo.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PrestamoRepo extends JpaRepository<Prestamo, Long> {

    long countByLectorIdAndEstadoIn(Long lectorId, Collection<EstadoPrestamo> estados);

    List<Prestamo> findByLectorIdAndEstadoIn(Long lectorId, Collection<EstadoPrestamo> estados);
}