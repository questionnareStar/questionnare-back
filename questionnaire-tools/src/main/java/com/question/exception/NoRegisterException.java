/*
 *  Copyright 2020-2020 问卷星球团队
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.question.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * 用户未注册
 * @author 问卷星球团队
 * @date 2021-7-25
 */
@Getter
public class NoRegisterException extends RuntimeException{

    private Integer status = BAD_REQUEST.value();

    public NoRegisterException(String msg){
        super(msg);
    }

    public NoRegisterException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}
