package com.example.easynotes.controller;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Image;
import com.example.easynotes.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Base64;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @GetMapping("/images")
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

//    @PostMapping("/images")
//    public Image createImage(@Valid @RequestBody Image image) {
//        return imageRepository.save(image);
//    }

    private static String bucketName = "faces2";
    private static String keyName = "test4";
    private static String uploadFileName = "Readme.md";

    @PostMapping("/image")
    public ResponseEntity<Image> uploadImage(@RequestBody Image image) {
        try{
            BasicAWSCredentials creds = new BasicAWSCredentials("AKIAJCXLL5WIXQWFB33Q", "a7aK7+hRD6NhRBRbHRAF+5VL63hGquhDio89JlbY");
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).build();
            
            System.out.println("HERE" + image.getFileName());
            File file = new File(uploadFileName);
            s3Client.putObject(new PutObjectRequest(
                    bucketName, keyName, file));

        } catch(
                AmazonServiceException ase)

        {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch(
                AmazonClientException ace)

        {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

        return new ResponseEntity<Image>(image, HttpStatus.OK);
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
//
//        image.setTitle(imageDetails.getTitle());
//        image.setContent(imageDetails.getContent());

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
