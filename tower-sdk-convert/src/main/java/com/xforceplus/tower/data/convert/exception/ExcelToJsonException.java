package com.xforceplus.tower.data.convert.exception;

import java.security.PrivilegedActionException;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.exception
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-08-20
 */
public class ExcelToJsonException extends RuntimeException {

    public ExcelToJsonException(String message) {
        super(message);
    }

    public ExcelToJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelToJsonException(Throwable cause) {
        super(cause);
    }
}
