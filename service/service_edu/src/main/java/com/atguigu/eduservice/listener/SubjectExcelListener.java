package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

/**
 * @PROJECT_NAME: guli_parent
 * @Author ：liuZA
 * @Description : TODO
 * @Date ：Created in 2020-07-14
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public EduSubjectService eduSubjectService;

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if (excelSubjectData==null) {
            throw new GuliException(20001,"请填写科目信息后导入!");
        }
        //判断数据库是否存在一级类目
        EduSubject existOneSubject = this.isExistOneSubject(eduSubjectService, excelSubjectData);
        if (existOneSubject == null) {
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(excelSubjectData.getOneSubject());
            existOneSubject.setParentId("0");
            eduSubjectService.save(existOneSubject);
        }
        //判断是否存在二级类目
        EduSubject existTwoSubject = this.isExistTwoSubject(eduSubjectService, excelSubjectData, existOneSubject.getId());
        if (existTwoSubject == null) {
            EduSubject eduSubject = new EduSubject();
            eduSubject.setTitle(excelSubjectData.getTwoSubject());
            eduSubject.setParentId(existOneSubject.getId());
            eduSubjectService.save(eduSubject);
        }
    }

    /**
     * 二级类目
     *
     * @param eduSubjectService
     * @param excelSubjectData
     * @param id
     * @return
     */
    public EduSubject isExistTwoSubject(EduSubjectService eduSubjectService, ExcelSubjectData excelSubjectData, String id) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", excelSubjectData.getTwoSubject())
                .eq("parent_id", id);
        EduSubject oneSubject = eduSubjectService.getOne(queryWrapper);
        return oneSubject;
    }

    /**
     * 判断一级
     *
     * @param eduSubjectService
     * @param excelSubjectData
     * @return
     */
    public EduSubject isExistOneSubject(EduSubjectService eduSubjectService, ExcelSubjectData excelSubjectData) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", excelSubjectData.getOneSubject())
                .eq("parent_id", "0");
        EduSubject oneSubject = eduSubjectService.getOne(queryWrapper);
        return oneSubject;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
