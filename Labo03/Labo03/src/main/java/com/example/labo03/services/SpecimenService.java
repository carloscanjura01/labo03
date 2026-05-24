package com.example.labo03.services;

import com.example.labo03.domain.dto.response.specimen.SpecimenResponse;
import com.example.labo03.domain.dto.request.CreateSpecimenRequest;
import com.example.labo03.domain.dto.request.UpdateSpecimenRequest;
import com.example.labo03.domain.dto.response.PageableResponse;
import com.example.labo03.domain.dto.response.specimen.SpecimenResponse;

import java.util.UUID;

public interface SpecimenService {

    SpecimenResponse createSpecimen(CreateSpecimenRequest request);

    PageableResponse<SpecimenResponse> getAllSpecimens(
            int page,
            int size,
            String sortBy,
            String sortOrder
    );

    SpecimenResponse getSpecimenById(UUID id);

    SpecimenResponse updateSpecimen(
            UUID id,
            UpdateSpecimenRequest request
    );

    SpecimenResponse deleteSpecimen(UUID id);
}
