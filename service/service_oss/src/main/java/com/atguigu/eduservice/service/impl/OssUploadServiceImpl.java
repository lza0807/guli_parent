package com.atguigu.eduservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.eduservice.service.IOssUploadService;
import com.atguigu.eduservice.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssUploadServiceImpl implements IOssUploadService {
    @Override
    public String uploadFile(MultipartFile file) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        originalFilename = uuid + originalFilename;
        //根据当前日期进行分类管理
        String dateTime = new DateTime().toString("yyyy/MM/dd");
        originalFilename = dateTime+originalFilename;
        //获得文件的输入流
        try {
            InputStream inputStream = file.getInputStream();
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucketName, originalFilename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            String uploadUrl = "http://" + bucketName + "." + endpoint + "/" + originalFilename;
            return uploadUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
