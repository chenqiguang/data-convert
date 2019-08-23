package com.xforceplus.tower.data.convert;

import com.xforceplus.tower.data.convert.model.ExcelToJsonProperty;
import com.xforceplus.tower.data.convert.model.JsonToExcelProperty;
import com.xforceplus.tower.data.convert.util.ExcelConvertUtil;

import java.util.List;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-08-21
 */
public class ExcelConvertFactory {
    public ExcelConvertFactory() {
    }

    public static String excelToJson(ExcelToJsonProperty property){
        return ExcelConvertUtil.excelToJson(property);
    }

    public static void jsonToExcel(List<JsonToExcelProperty> properties,String fileName){
        ExcelConvertUtil.jsonToExcel(properties,fileName);

    }
}
