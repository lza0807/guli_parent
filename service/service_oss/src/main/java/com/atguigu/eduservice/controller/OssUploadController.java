package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.service.IOssUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api("阿里云文件上传")
@RequestMapping("/oss/upload")
@CrossOrigin
public class OssUploadController {

    @Autowired
    private IOssUploadService ossUploadService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R ossUpload(MultipartFile file){

        String url = ossUploadService.uploadFile(file);
        return R.ok().data("url",url);

    }

}
