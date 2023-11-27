package com.clean.code.springboot56.service;

import com.clean.code.springboot56.domain.FileStorage;
import com.clean.code.springboot56.domain.FileStorageStatus;
import com.clean.code.springboot56.repository.FileStorageRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileStorageService {
    @Value("${upload.folder}")
    private String uploadFolder;
    private final Hashids hashids;
    private final FileStorageRepository fileStorageRepository;

    public FileStorageService( FileStorageRepository fileStorageRepository) {
        this.hashids = new Hashids(getClass().getName(), 6);
        this.fileStorageRepository = fileStorageRepository;
    }
    public void save(MultipartFile multipartFile){
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStorage.setFileSize((int) multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setFileStorageStatus(FileStorageStatus.DRAFT);
        fileStorageRepository.save(fileStorage);

        Date now = new Date();
         File uploader = new File(String.format("%s/upload_files/%d/%d/%d", this.uploadFolder,
                 1900 + now.getYear(), 1+ now.getMonth(), now.getDate() ));
         if(!uploader.exists() && uploader.mkdirs()){
             System.out.println("aytilgan papkalar yaratildi");
         }
         fileStorage.setHashId(hashids.encode(fileStorage.getId()));
         fileStorage.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s",
                 1900+now.getYear(),
                 1+ now.getMonth(),
                 now.getDate(),
                 fileStorage.getHashId(),
                 fileStorage.getExtension()));
         fileStorageRepository.save(fileStorage);
         uploader = uploader.getAbsoluteFile();
         File file = new File(uploader, String.format("%s.%s", fileStorage.getHashId(), fileStorage.getExtension()));
         try{
             multipartFile.transferTo(file);
         }catch (IOException e){
             e.printStackTrace();
         }



    }
    @Transactional(readOnly = true)
        public FileStorage findByHashId(String hashId){
        return fileStorageRepository.findByHashId(hashId);
    }

    private String getExt(String fileName){
        String ext = null;
            if(fileName != null && !fileName.isEmpty()){
                int dot = fileName.lastIndexOf('.');
                if (dot > 0 && dot <= fileName.length()-2){
                    ext = fileName.substring( dot + 1);
                }
            }
            return  ext;
    }
    public void deleteFile(String hashId){
        FileStorage fileStorage = findByHashId(hashId);
        File file = new File(String.format("%s/%s", this.uploadFolder, fileStorage.getUploadPath()));
        if (file.delete()){
            fileStorageRepository.delete(fileStorage);
        }
    }

    @Scheduled (cron = "0 10 20 * * *")
    public void deleteAllDraft(){

        List<FileStorage> fileStorageList = fileStorageRepository.findByFileStorageStatus(FileStorageStatus.DRAFT);
        for (FileStorage fileStorage: fileStorageList) {
            deleteFile(fileStorage.getHashId());

        }


    }
    public void delete(Long id){
        FileStorage fileStorage = fileStorageRepository.getReferenceById(id);
        fileStorageRepository.delete(fileStorage);
    }
}