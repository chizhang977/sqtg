package com.justin.sqtg.product.controller;

import com.justin.sqtg.common.result.Result;
import com.justin.sqtg.product.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/admin/product")
@CrossOrigin
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    //图片上传
    @ApiOperation("图片上传")
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file){
        String url = fileUploadService.uploadFile(file);
        return Result.ok(url);
    }
}
