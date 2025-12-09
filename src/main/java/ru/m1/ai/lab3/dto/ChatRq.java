package ru.m1.ai.lab3.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatRq {

    @NotBlank
    private String message;
}
