package com.question.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 问卷星球团队
 * @version 1.0
 * @date 2021/7/25 22:18
 */
@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    private String windows;

    private String linux;
}
