package com.xforceplus.tower.data.convert.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.test
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-10-15
 */
@SpringBootApplication
@EnableFeignClients(value = "com.xforceplus.tower")
@ComponentScan(value="com.xforceplus.tower")
public class DataTemplateApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(DataTemplateApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
