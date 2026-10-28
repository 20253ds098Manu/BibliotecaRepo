package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Prestamos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "lector_id", nullable = false)
    private Lector lector;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;
    @Column (nullable = false)
    private LocalDate fechaPrestamo;
    @Column (nullable = false)
    private LocalDate fechaDevolucionMaxima;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrestamo estado;
}
