package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.entity.subject.SubjectVo;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.PrinterAbortException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lza
 * @since 2020-07-14
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    private EduSubjectService eduSubjectService;

    @Override
    public void importFile(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(eduSubjectService))
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<SubjectVo> getAllSubject() {
        //查询出所有的一级科目
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(queryWrapper);
        //查询出所有的二级科目
        LambdaQueryWrapper<EduSubject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.ne(EduSubject::getParentId,"0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(lambdaQueryWrapper);

        List<SubjectVo> subjectVoList = new ArrayList<>();
        //遍历一级科目
        oneSubjectList.forEach(v->{
            SubjectVo subjectVo = new SubjectVo();
            BeanUtils.copyProperties(v,subjectVo);
            subjectVoList.add(subjectVo);

            //遍历二级科目,比对二级科目的pid是否与一级科目的ID相同
            List<SubjectVo> twoList = new ArrayList<>();
            twoSubjectList.forEach(t->{
                if (v.getId().equals(t.getParentId())){
                    SubjectVo twoSubject = new SubjectVo();
                    BeanUtils.copyProperties(t,twoSubject);
                    twoList.add(twoSubject);
                }
            });
            subjectVo.setChildren(twoList);
        });
        return subjectVoList;
    }
}
