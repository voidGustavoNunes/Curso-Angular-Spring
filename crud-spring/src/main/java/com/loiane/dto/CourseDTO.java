package com.loiane.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

public record CourseDTO(
        @JsonProperty("_id") Long id,
        @NotBlank @NotNull @Length(min = 5, max = 100) String name,
        @NotNull @Length(max = 10) @Pattern(regexp = "Back-end|Front-end") String category,
        String fileName,
        Long fileSize) {

    public CourseDTO(Long id, String name, String category) {
        this(id, name, category, null, null);
        }
}
