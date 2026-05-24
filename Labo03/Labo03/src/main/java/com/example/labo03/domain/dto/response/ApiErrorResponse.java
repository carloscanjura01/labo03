package com.example.labo03.domain.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    private String message;
    private Integer status;
    private LocalDateTime timestamp;
}
