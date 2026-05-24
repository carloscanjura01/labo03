package com.example.labo03.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.labo03.domain.dto.request.CreateSpecimenRequest;
import com.example.labo03.domain.dto.request.UpdateSpecimenRequest;
import com.example.labo03.domain.dto.response.GeneralResponse;
import com.example.labo03.services.SpecimenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/specimens")
@RequiredArgsConstructor
public class SpecimenController {

    private final SpecimenService specimenService;

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createSpecimen(
            @RequestBody @Valid CreateSpecimenRequest request
    ) {

        return buildResponse(
                "Specimen created successfully",
                HttpStatus.CREATED,
                specimenService.createSpecimen(request)
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<GeneralResponse> getAllSpecimens(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {

        return buildResponse(
                "Specimens found",
                HttpStatus.OK,
                specimenService.getAllSpecimens(
                        page,
                        size,
                        sortBy,
                        sortOrder
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getSpecimenById(
            @PathVariable UUID id
    ) {

        return buildResponse(
                "Specimen found",
                HttpStatus.OK,
                specimenService.getSpecimenById(id)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GeneralResponse> updateSpecimen(
            @PathVariable UUID id,
            @RequestBody UpdateSpecimenRequest request
    ) {

        return buildResponse(
                "Specimen updated",
                HttpStatus.OK,
                specimenService.updateSpecimen(id, request)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeneralResponse> deleteSpecimen(
            @PathVariable UUID id
    ) {

        return buildResponse(
                "Specimen deleted",
                HttpStatus.OK,
                specimenService.deleteSpecimen(id)
        );
    }

    private ResponseEntity<GeneralResponse> buildResponse(
            String message,
            HttpStatus status,
            Object data
    ) {

        GeneralResponse response =
                GeneralResponse.builder()
                        .message(message)
                        .status(status.value())
                        .timestamp(LocalDateTime.now())
                        .data(data)
                        .build();

        return new ResponseEntity<>(response, status);
    }
}