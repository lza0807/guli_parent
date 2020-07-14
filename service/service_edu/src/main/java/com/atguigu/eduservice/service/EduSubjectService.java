package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.SubjectVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lza
 * @since 2020-07-14
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 导入课程科目
     * @param file
     * @return
     */
    void importFile(MultipartFile file);

    /**
     * 所有课程科目信息
     * @return
     */
    List<SubjectVo> getAllSubject();
}
