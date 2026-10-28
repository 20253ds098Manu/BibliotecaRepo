package com.example.demo.repository;

import com.example.demo.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  LibroRepo extends JpaRepository <Libro, Long> {
}
