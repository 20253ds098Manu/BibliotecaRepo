package com.example.demo.controller.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrestamoRequestDTO {

    @NotNull(message = "El id del lector es obligatorio")
    private Long lectorId;

    @NotNull(message = "El id del libro es obligatorio")
    private Long libroId;
}