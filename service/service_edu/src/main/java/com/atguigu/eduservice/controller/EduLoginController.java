package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @PROJECT_NAME: guli_parent
 * @Author ：liuZA
 * @Description : TODO
 * @Date ：Created in 2020-07-10
 */
@CrossOrigin
@RestController
@RequestMapping("eduservice/user")
public class EduLoginController {

    /**
     * 登录
     * @return
     */
    @PostMapping("login")
    public R login() {

        return R.ok().data("token","admin");
    }

    /**
     * 信息
     * @return
     */
    @GetMapping("info")
    public R info() {

        return R.ok().data("roles","admin").data("name","admin").data("avatar","");
    }

}
