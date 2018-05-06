package com.example.easynotes;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

@SpringBootApplication
@EnableJpaAuditing
public class ImageApplication {
	private static String bucketName = "faces2";
	private static String keyName = "test2";
	private static String uploadFileName = "Readme.md";


	public static void main(String[] args) throws IOException {

		SpringApplication.run(ImageApplication.class, args);
	}
}
