package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @PROJECT_NAME: guli_parent
 * @Author ：liuZA
 * @Description : TODO
 * @Date ：Created in 2020-07-14
 */
@Data
public class SubjectVo {
    private String id;
    private String title;
    List<SubjectVo> children = new ArrayList<>(11);
}
