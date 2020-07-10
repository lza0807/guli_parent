package com.atguigu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @PROJECT_NAME: guli_parent
 * @Author ：liuZA
 * @Description : TODO
 * @Date ：Created in 2020-07-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException {
    private Integer code;
    private String msg;
}
