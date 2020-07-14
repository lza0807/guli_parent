package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @PROJECT_NAME: guli_parent
 * @Author ：liuZA
 * @Description : TODO
 * @Date ：Created in 2020-07-14
 */
@Data
public class ExcelSubjectData {
    @ExcelProperty(index = 0)
    private String oneSubject;
    @ExcelProperty(index = 1)
    private String twoSubject;
}
