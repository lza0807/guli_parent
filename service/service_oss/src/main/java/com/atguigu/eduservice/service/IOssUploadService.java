package com.atguigu.eduservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface IOssUploadService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    String uploadFile(MultipartFile file);
}
