package com.example.demo.controller.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectorRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private Boolean membresiaPremium;
}