package com.ws.hotelsearch.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;

@Service
public interface MinioService {

    boolean isObjectExist(String name, String bucket);

    void createBucketIfNotExist(String name);

    Optional<String> getPreSignedUrl(String filename, String bucket) throws RuntimeException;

    boolean putFile(String id, InputStream inputStream, String bucket);
}
