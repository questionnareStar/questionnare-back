package com.question.domain;

import com.question.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 问卷星球团队
 * @version 1.0
 * @date 2021/7/24 18:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    /**
     * 提示信息
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public Result(ResultEnum resultEnum, T obj) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = obj;
    }


    public static Result setData(ResultEnum resultEnum, Object obj) {
        return new Result(resultEnum, obj);
    }

    public static Result success(Object obj) {
        return new Result(ResultEnum.SUCCESS, obj);
    }
}
