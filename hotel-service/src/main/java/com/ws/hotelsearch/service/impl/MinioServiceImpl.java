package com.ws.hotelsearch.service.impl;

import com.ws.hotelsearch.service.MinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioServiceImpl implements MinioService {

    private final static int EXPIRY_DURATION = 5;

    private final MinioClient minioClient;

    @Override
    public boolean isObjectExist(String name, String bucket) {
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucket)
                    .object(name)
                    .build());
            return true;
        } catch (ErrorResponseException e) {
            return false;
        } catch (InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException |
                 IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            log.error("Cannot check if object exist");
            return false;
        }
    }

    @Override
    public void createBucketIfNotExist(String name) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
                log.info("Bucket {} created", name);
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            log.error("Cannot make bucket OR check if bucket exist");
        }
    }


    @Override
    public Optional<String> getPreSignedUrl(String filename, String bucket) throws RuntimeException {
        try {
            GetPresignedObjectUrlArgs args = prepareObjectArgsToGetPresignedUrl(filename, bucket);
            String urlOpt = minioClient.getPresignedObjectUrl(args);
            return Optional.ofNullable(urlOpt);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException |
                 ServerException e) {
            log.error("Unable to get presigned url to download file with name: {}.", filename);
            return Optional.empty();
        }
    }

    @Override
    public boolean putFile(String id, InputStream inputStream, String bucket) {
        try {
            PutObjectArgs args = prepareObjectArgsToUpload(id, inputStream, bucket);
            minioClient.putObject(args);
            return true;
        } catch (ErrorResponseException | InvalidKeyException | InvalidResponseException |
                 NoSuchAlgorithmException | ServerException | XmlParserException | InsufficientDataException |
                 InternalException | IOException e) {
            log.error("Unable to upload file to storage.");
            return false;
        }
    }

    private PutObjectArgs prepareObjectArgsToUpload(String id, InputStream inputStream, String bucket) throws IOException {
        return PutObjectArgs.builder()
                .bucket(bucket)
                .object(id)
                .stream(inputStream, inputStream.available(), -1)
                .build();
    }

    private GetPresignedObjectUrlArgs prepareObjectArgsToGetPresignedUrl(String filename, String bucket) {
        return GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucket)
                .object(filename)
                .expiry(EXPIRY_DURATION, TimeUnit.MINUTES)
                .build();
    }

}