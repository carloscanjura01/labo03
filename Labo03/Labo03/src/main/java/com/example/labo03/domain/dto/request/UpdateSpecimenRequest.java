package com.example.labo03.domain.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSpecimenRequest {

    private String name;
    private String region;
    private Integer dangerLevel;
    private Boolean isFriendly;
}