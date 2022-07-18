package com.ws.hotelsearch.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileStorageService {

    boolean isImageExist(String name);

    Optional<String> uploadImage(MultipartFile file);



    List<String> uploadImages(List<MultipartFile> fileList);

    String getDownloadUrl(String name);
}
