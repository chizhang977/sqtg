package com.justin.sqtg.product.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.justin.sqtg.product.service.FileUploadService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${aliyun.endpoint}")
    private String endpoint;

    @Value("${aliyun.keyid}")
    private String accessKeyId;

    @Value("${aliyun.keysecret}")
    private String accessKeySecret;

    @Value("${aliyun.bucketname}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            //上传文件输入流
            InputStream inputStream = file.getInputStream();
            String objectName = file.getOriginalFilename();
            String uuid  = UUID.randomUUID().toString().replaceAll("-", "");
            objectName = uuid + objectName;

            //文件分组
            String timeUrl = new DateTime().toString("yyyy/MM/dd");

            objectName = timeUrl + "/" + objectName;
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);

            putObjectRequest.setProcess("true");
            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            // 如果上传成功，则返回200。
            System.out.println(result.getResponse().getStatusCode());
            System.out.println(result.getResponse().getErrorResponseAsString());
            System.out.println(result.getResponse().getUri());
            //返回上传图片在阿里云路径
            String url = result.getResponse().getUri();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
