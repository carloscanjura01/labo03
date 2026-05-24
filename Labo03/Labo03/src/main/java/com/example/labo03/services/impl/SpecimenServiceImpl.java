package com.example.labo03.services.impl;

import lombok.RequiredArgsConstructor;
import com.example.labo03.common.mappers.SpecimenMapper;
import com.example.labo03.domain.dto.request.CreateSpecimenRequest;
import com.example.labo03.domain.dto.request.UpdateSpecimenRequest;
import com.example.labo03.domain.dto.response.PageableResponse;
import com.example.labo03.domain.dto.response.specimen.SpecimenResponse;
import com.example.labo03.domain.entities.Specimen;
import com.example.labo03.exceptions.ResourceNotFoundException;
import com.example.labo03.repositories.SpecimenRepository;
import com.example.labo03.services.SpecimenService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecimenServiceImpl implements SpecimenService {

    private final SpecimenRepository specimenRepository;
    private final SpecimenMapper specimenMapper;

    @Override
    @Transactional
    public SpecimenResponse createSpecimen(CreateSpecimenRequest request) {

        Specimen specimen = specimenMapper.toEntity(request);

        return specimenMapper.toDto(
                specimenRepository.save(specimen)
        );
    }

    @Override
    public PageableResponse<SpecimenResponse> getAllSpecimens(
            int page,
            int size,
            String sortBy,
            String sortOrder
    ) {

        Sort sort = sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SpecimenResponse> specimenPage =
                specimenMapper.toDtoList(
                        specimenRepository.findAll(pageable)
                );

        if (specimenPage.getTotalElements() == 0)
            throw new ResourceNotFoundException(
                    "No specimens are registered in Hyrule"
            );

        return PageableResponse.<SpecimenResponse>builder()
                .content(specimenPage.getContent())
                .page(specimenPage.getNumber())
                .size(specimenPage.getSize())
                .totalElements(specimenPage.getTotalElements())
                .totalPages(specimenPage.getTotalPages())
                .last(specimenPage.isLast())
                .build();
    }

    @Override
    public SpecimenResponse getSpecimenById(UUID id) {

        return specimenMapper.toDto(
                specimenRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Specimen not found in Hyrule Records"
                                )
                        )
        );
    }

    @Override
    @Transactional
    public SpecimenResponse updateSpecimen(
            UUID id,
            UpdateSpecimenRequest request
    ) {

        this.getSpecimenById(id);

        Specimen specimen =
                specimenMapper.toEntityUpdate(request, id);

        return specimenMapper.toDto(
                specimenRepository.save(specimen)
        );
    }

    @Override
    @Transactional
    public SpecimenResponse deleteSpecimen(UUID id) {

        SpecimenResponse existingSpecimen =
                this.getSpecimenById(id);

        specimenRepository.deleteById(id);

        return existingSpecimen;
    }
}