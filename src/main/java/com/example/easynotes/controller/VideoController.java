package com.example.easynotes.controller;

import java.io.FileOutputStream;
import java.net.URI;

import com.example.easynotes.repository.VideoRepository;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Video;
import com.example.easynotes.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class VideoController {

    @Autowired
    VideoRepository videoRepository;

    @GetMapping("/videos")
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

//    @PostMapping("/videos")
//    public Video createVideo(@Valid @RequestBody Video video) {
//        return videoRepository.save(video);
//    }

    private static String bucketName = "faces2";
    private static String keyName = "test4";
    private static String uploadFileName = "Readme.md";

    @PostMapping("/video")
    public void uploadVideo(@RequestBody Video video)  throws Exception {
//        Base64.Decoder decoder = Base64.getDecoder();
//        byte[] decodedByte = decoder.decode(video.getFileName().split(",")[1]);
//        File file = new File("test.webm");
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//
//        FileOutputStream fos = new FileOutputStream(file);
//        fos.write(decodedByte);
//        fos.close();
//
//        return new ResponseEntity<Video>(video, HttpStatus.ACCEPTED);
////        HttpClient httpclient = HttpClients.createDefault();
//
//        try {
//            URIBuilder builder = new URIBuilder("https://api.videoindexer.ai/{location}/Accounts/{accountId}/Videos?accessToken={accessToken}&name={name}");
//            //builder.setParameter("videoUrl", "/Users/nke09/javaspringcrud/src/main/java/com/example/easynotes/controller/test.mp4");
////            builder.setParameter("language", "{string}");
////            builder.setParameter("externalId", "{string}");
////            builder.setParameter("metadata", "{string}");
////            builder.setParameter("description", "{string}");
////            builder.setParameter("partition", "{string}");
////            builder.setParameter("callbackUrl", "{string}");
////            builder.setParameter("indexingPreset", "{string}");
////            builder.setParameter("streamingPreset", "{string}");
////            builder.setParameter("linguisticModelId", "{string}");
//
//
//            URI uri = builder.build();
//            HttpPost request = new HttpPost(uri);
////            request.setHeader("Content-Type", "multipart/form-data");
//            request.setHeader("Ocp-Apim-Subscription-Key", "6f3075ad9b9848cdbbe3950846775487\n");


            // Request body
//            StringEntity reqEntity = new StringEntity("{body}");


//            HttpEntity reqEntity = MultipartEntityBuilder.create()
//                    .addPart("bin", bin)
//                    .build();

//            request.setEntity(reqEntity);

//            HttpResponse response = httpclient.execute(request);
//            HttpEntity entity = response.getEntity();
//
//            if (entity != null) {
//                System.out.println(EntityUtils.toString(entity));
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }


    }



    @GetMapping("/videos/{id}")
    public Video getVideoById(@PathVariable(value = "id") Long videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", videoId));
    }

    @PutMapping("/videos/{id}")
    public Video updateVideo(@PathVariable(value = "id") Long videoId,
                                           @Valid @RequestBody Video videoDetails) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", videoId));
//
//        video.setTitle(videoDetails.getTitle());
//        video.setContent(videoDetails.getContent());

        Video updatedVideo = videoRepository.save(video);
        return updatedVideo;
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable(value = "id") Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", videoId));

        videoRepository.delete(video);

        return ResponseEntity.ok().build();
    }
}
