package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Image;
import com.example.easynotes.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */
@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @GetMapping("/images")
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @PostMapping("/images")
    public Image createImage(@Valid @RequestBody Image image) {
        return imageRepository.save(image);
    }

    @GetMapping("/images/{id}")
    public Image getImageById(@PathVariable(value = "id") Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));
    }

    @PutMapping("/images/{id}")
    public Image updateImage(@PathVariable(value = "id") Long imageId,
                                           @Valid @RequestBody Image imageDetails) {

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));

        image.setTitle(imageDetails.getTitle());
        image.setContent(imageDetails.getContent());

        Image updatedImage = imageRepository.save(image);
        return updatedImage;
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable(value = "id") Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));

        imageRepository.delete(image);

        return ResponseEntity.ok().build();
    }
}
