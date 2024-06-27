package dev.bookstore.creeper.demo.controller;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import dev.bookstore.creeper.demo.dto.FileUploadOkResponseDTO;
import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            logger.info("Uploading file: " + file.getOriginalFilename());
            String originalFilename = file.getOriginalFilename();
            String filenameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            File tempFile = File.createTempFile(filenameWithoutExtension + "-", extension);

            file.transferTo(tempFile);

            // 使用Unirest发送请求给图片服务器
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post("http://localhost:10339/upload")
                    .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                    .header("Content-Type", "multipart/form-data")
                    .field("file", tempFile)
                    .asString();

            if (response.getStatus() != 200) {
                throw new Exception("Failed to upload file");
            }

            System.out.println(response.getBody());

            Gson gson = new Gson();
            FileUploadOkResponseDTO dto = gson.fromJson(response.getBody(), FileUploadOkResponseDTO.class);

            String imageUrl = dto.getUrl();

            // 返回图片URL给前端
            return ResponseEntity.ok(new GeneralResponseDTO(true, imageUrl));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GeneralResponseDTO(false, "Failed to upload file"));
        }
    }
}
