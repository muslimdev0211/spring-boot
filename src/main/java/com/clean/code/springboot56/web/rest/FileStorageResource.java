package com.clean.code.springboot56.web.rest;

import com.clean.code.springboot56.domain.FileStorage;
import com.clean.code.springboot56.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class FileStorageResource {
    private final FileStorageService fileStorageService;
    @Value("${upload.folder}")
    private String uploadFolder;

    public FileStorageResource(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    @PostMapping("/upload")
    public ResponseEntity upload (@RequestParam("file") MultipartFile multipartFile){
        fileStorageService.save(multipartFile );
        return ResponseEntity.ok(multipartFile.getOriginalFilename() + "Saving Completed");

    }
    @DeleteMapping ("/upload/{hashId}")
    public ResponseEntity delete(@PathVariable String hashId){
        FileStorage fileStorage = fileStorageService.findByHashId(hashId);
        Long id = fileStorage.getId();
        fileStorageService.delete(id);

        return ResponseEntity.ok("File is Removen");

    }
    @GetMapping("/preview/{hashId}")
    public ResponseEntity preview(@PathVariable String hashId) throws IOException {

        FileStorage fileStorage = fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath())));
    }

    @GetMapping("/download/{hashId}")
    public ResponseEntity downloadFile(@PathVariable String hashId) throws IOException {

        FileStorage fileStorage = fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath())));
    }
    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity deleteFIle(@PathVariable String hashId){
        fileStorageService.deleteFile(hashId);
        return  ResponseEntity.ok("Fayl o'chirildi");
    }
}
