package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
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
@RequestMapping("eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduTeacherMapper eduTeacherMapper;

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
        try {
            int a = 10 / 0;
        } catch (Exception e) {
            throw new GuliException(20001,"出现自定义异常!!!");
        }
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

    /**
     * 条件分页查询
     *
     * @param page
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "条件分页查询")
    @PostMapping("pageCond/{page}/{limit}")
    public R SearchTeachersPageListAndTJ(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable(value = "page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable(value = "limit") Long limit,
            @RequestBody TeacherQuery teacherQuery) {
        Page<EduTeacher> eduTeacherPage = new Page<>(page, limit);
        LambdaQueryWrapper<EduTeacher> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper
                .eq(!StringUtils.isEmpty(teacherQuery.getLevel()), EduTeacher::getLevel, teacherQuery.getLevel())
                .le(!StringUtils.isEmpty(teacherQuery.getEnd()),EduTeacher::getGmtCreate,teacherQuery.getEnd())
                .ge(!StringUtils.isEmpty(teacherQuery.getBegin()),EduTeacher::getGmtCreate,teacherQuery.getBegin())
                .orderByDesc(EduTeacher::getGmtCreate);
        eduTeacherMapper.selectPage(eduTeacherPage, lambdaQueryWrapper);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();
        return R.ok().data("total", total).data("items", records);
    }

    /**
     * 讲师添加
     *
     * @param eduTeacher
     * @return
     */
    @ApiOperation("讲师添加")
    @PostMapping("addEduTeacher")
    public R addEduTeacher(
            @ApiParam(value = "讲师信息", required = true)
            @RequestBody EduTeacher eduTeacher) {
        if (ObjectUtils.isEmpty(eduTeacher)) {
            return R.error();
        }
        boolean save = teacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    /**
     * 讲师修改
     * @param eduTeacher
     * @return
     */
    @ApiOperation("讲师修改")
    @PutMapping
    public R editEduTeacher(
            @RequestBody EduTeacher eduTeacher) {
        boolean b = teacherService.updateById(eduTeacher);
        return b ? R.ok() : R.error();
    }

    @ApiOperation("根据id查询讲师信息")
    @GetMapping("{id}")
    public R getTeacherInfoById(
            @ApiParam(name = "id",value = "讲师id")
            @PathVariable(value = "id") String id){

        EduTeacher teacher = teacherService.getById(id);

        return R.ok().data("item",teacher);
    }


}

