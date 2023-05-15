package com.loiane.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loiane.dto.CourseDTO;
import com.loiane.service.CourseService;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    private String uploadPath;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
        this.uploadPath = "C:/Users/Gustavo/Documents/upload/";
    }

    @GetMapping
    public List<CourseDTO> list() {
        return courseService.list();
    }

    @GetMapping("/{id}")
    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CourseDTO create(@RequestBody @Valid @NotNull CourseDTO course) {
        return courseService.create(course);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createWithResponse(@RequestBody @Valid @NotNull CourseDTO course) {
        CourseDTO createdCourse = courseService.create(course);
        return ResponseEntity.ok(createdCourse);
    }

    @PutMapping("/{id}")
    public CourseDTO update(@PathVariable @NotNull @Positive Long id,
            @RequestBody @Valid @NotNull CourseDTO course) {
        return courseService.update(id, course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        courseService.delete(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> uploadFile(@PathVariable @NotNull @Positive Long id, 
        @RequestParam("file") MultipartFile file, 
        @RequestParam("fileName") @NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[a-zA-Z0-9._-]+$") String fileName) {

        String finalFileName = id + "_" + fileName;
        Path path = Paths.get(uploadPath + finalFileName);

        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading file. " + e.getMessage());
        }

        return ResponseEntity.ok("File uploaded successfully.");
    }
    
}





