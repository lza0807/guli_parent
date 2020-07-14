package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lza
 * @since 2020-07-14
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 导入课程科目
     * @param file
     * @return
     */
    @PostMapping("/import")
    @ApiOperation("导入课程科目")
    public R importSubject(MultipartFile file){
        eduSubjectService.importFile(file);
        return R.ok();
    }
}

