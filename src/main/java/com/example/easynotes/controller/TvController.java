package com.example.easynotes.controller;


import com.example.easynotes.model.Tv;
import com.example.easynotes.repository.TvRepository;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.videointelligence.v1.*;
import com.google.cloud.videointelligence.v1.Entity;
import com.google.cloud.videointelligence.v1.LabelSegment;
import com.google.cloud.videointelligence.v1.AnnotateVideoRequest;
import com.google.cloud.videointelligence.v1.VideoAnnotationResults;

import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class TvController {


//    @Autowired
//    TvRepository tvRepository;


    @PostMapping("/tv")
    public void analyseTv(@RequestBody Tv tv)  throws Exception {
        try (VideoIntelligenceServiceClient client = VideoIntelligenceServiceClient.create()) {
            byte[] encodedBytes = tv.getFileName().split(",")[1].getBytes();

            AnnotateVideoRequest request = AnnotateVideoRequest.newBuilder()
                    .setInputContent(ByteString.copyFrom(encodedBytes))
                    .addFeatures(Feature.LABEL_DETECTION)
                    .build();

            // Create an operation that will contain the response when the operation completes.
            OperationFuture<AnnotateVideoResponse, AnnotateVideoProgress> response =
                    client.annotateVideoAsync(request);

            System.out.println("Waiting for operation to complete...");
            for (VideoAnnotationResults results : response.get().getAnnotationResultsList()) {
                // process video / segment level label annotations
                System.out.println("Locations: ");
                for (LabelAnnotation labelAnnotation : results.getSegmentLabelAnnotationsList()) {
                    System.out
                            .println("Video label: " + labelAnnotation.getEntity().getDescription());
                    // categories
                    for (Entity categoryEntity : labelAnnotation.getCategoryEntitiesList()) {
                        System.out.println("Video label category: " + categoryEntity.getDescription());
                    }
                    // segments
                    for (LabelSegment segment : labelAnnotation.getSegmentsList()) {
                        double startTime = segment.getSegment().getStartTimeOffset().getSeconds()
                                + segment.getSegment().getStartTimeOffset().getNanos() / 1e9;
                        double endTime = segment.getSegment().getEndTimeOffset().getSeconds()
                                + segment.getSegment().getEndTimeOffset().getNanos() / 1e9;
                        System.out.printf("Segment location: %.3f:%.2f\n", startTime, endTime);
                        System.out.println("Confidence: " + segment.getConfidence());
                    }
                }

                // process shot label annotations
                for (LabelAnnotation labelAnnotation : results.getShotLabelAnnotationsList()) {
                    System.out
                            .println("Shot label: " + labelAnnotation.getEntity().getDescription());
                    // categories
                    for (Entity categoryEntity : labelAnnotation.getCategoryEntitiesList()) {
                        System.out.println("Shot label category: " + categoryEntity.getDescription());
                    }
                    // segments
                    for (LabelSegment segment : labelAnnotation.getSegmentsList()) {
                        double startTime = segment.getSegment().getStartTimeOffset().getSeconds()
                                + segment.getSegment().getStartTimeOffset().getNanos() / 1e9;
                        double endTime = segment.getSegment().getEndTimeOffset().getSeconds()
                                + segment.getSegment().getEndTimeOffset().getNanos() / 1e9;
                        System.out.printf("Segment location: %.3f:%.2f\n", startTime, endTime);
                        System.out.println("Confidence: " + segment.getConfidence());
                    }
                }

                // process frame label annotations
                for (LabelAnnotation labelAnnotation : results.getFrameLabelAnnotationsList()) {
                    System.out
                            .println("Frame label: " + labelAnnotation.getEntity().getDescription());
                    // categories
                    for (Entity categoryEntity : labelAnnotation.getCategoryEntitiesList()) {
                        System.out.println("Frame label category: " + categoryEntity.getDescription());
                    }
                    // segments
                    for (LabelSegment segment : labelAnnotation.getSegmentsList()) {
                        double startTime = segment.getSegment().getStartTimeOffset().getSeconds()
                                + segment.getSegment().getStartTimeOffset().getNanos() / 1e9;
                        double endTime = segment.getSegment().getEndTimeOffset().getSeconds()
                                + segment.getSegment().getEndTimeOffset().getNanos() / 1e9;
                        System.out.printf("Segment location: %.3f:%.2f\n", startTime, endTime);
                        System.out.println("Confidence: " + segment.getConfidence());
                    }
                }
            }
        }
    }
}
