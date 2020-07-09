package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-02-24
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * //1 查询讲师表所有数据
     *
     * @return
     */
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用service的方法实现查询所有的操作
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().ne(EduTeacher::getId, 0);
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * //2 逻辑删除讲师的方法
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable(value = "id") String id) {
        boolean b = teacherService.removeById(id);
        return b == true ? R.ok() : R.error();
    }

    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("{page}/{limit}")
    public R SearchTeachersPageList(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable(value = "page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable(value = "limit") Long limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(page, limit);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().ne(EduTeacher::getId, 0);
        teacherService.page(eduTeacherPage, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", eduTeacherPage.getTotal());
        map.put("items", eduTeacherPage.getRecords());
        return R.ok().data(map);
    }

    @ApiOperation(value = "条件分页查询")
    @GetMapping("pageCond/{page}/{limit}")
    public R SearchTeachersPageListAndTJ(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable(value = "page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable(value = "limit") Long limit,
            TeacherQuery teacherQuery) {
        Page<EduTeacher> eduTeacherPage = new Page<>(page, limit);
        LambdaQueryWrapper<EduTeacher> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper
                .like(StringUtils.isEmpty(teacherQuery.getName()), EduTeacher::getName, teacherQuery.getName())
                .ne(EduTeacher::getId,1);
        teacherService.page(eduTeacherPage, lambdaQueryWrapper);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();
        return R.ok().data("total",total).data("items",records);
    }
}

