package com.ws.hotelsearch.service.impl;

import com.ws.hotelsearch.exception.minio.EmptyUploadedFileException;
import com.ws.hotelsearch.exception.minio.UnsupportedMediaTypeException;
import com.ws.hotelsearch.service.FileStorageService;
import com.ws.hotelsearch.service.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final MinioService minioService;

    @Value("${minio.bucket.name}")
    private String imageBucket;

    @PostConstruct
    private void init() {
        minioService.createBucketIfNotExist(imageBucket);
    }

    @Override
    public boolean isImageExist(String name) {
        return minioService.isObjectExist(name, imageBucket);
    }

    @Override
    public Optional<String> uploadImage(MultipartFile file) {
        if (notEmptyFile(file) && isImageFile(file)) {
            try {
                String id = UUID.randomUUID().toString();
                InputStream inputStream = file.getInputStream();

                boolean successfulUpload = minioService.putFile(id, inputStream, imageBucket);

                return successfulUpload ? Optional.of(id) : Optional.empty();
            } catch (IOException e) {
                log.warn("Unable to extract input stream from image file");
            }
        }
        return Optional.empty();
    }

    @Override
    public List<String> uploadImages(List<MultipartFile> imagesToUploadList) {
        List<String> uploadedImages = imagesToUploadList.stream()
                .parallel()
                .map(this::uploadImage)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        boolean success = checkSum(imagesToUploadList, uploadedImages);

        return (success) ? uploadedImages : addFilesToDeleteJob(uploadedImages);
    }

    @Override
    public String getDownloadUrl(String name) {
        if (isImageExist(name)) {
            Optional<String> urlOpt = minioService.getPreSignedUrl(name, imageBucket);
            return urlOpt.orElse("empty");
        } else {
            log.info("Object not exist in file storage. Initialized delete operation");
            //TODO add delete from db option
            return "empty";
        }
    }

    private List<String> addFilesToDeleteJob(List<String> uploadedFiles) {
        log.warn("Missing files while download");
        //TODO add to spring batch delete operation
        throw new RuntimeException();
    }

    private boolean checkSum(List<MultipartFile> before, List<String> after) {
        return after.size() == before.size();
    }

    private boolean notEmptyFile(MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty()) {
            log.warn("Cannot upload empty file");
            return false;
        }
        return true;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (Objects.nonNull(contentType) && contentType.startsWith("image/")) {
            return true;
        }
        log.warn("Uploaded file is not image.");
        return false;
    }
}
