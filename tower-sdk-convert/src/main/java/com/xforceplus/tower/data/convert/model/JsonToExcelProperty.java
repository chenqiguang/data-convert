package com.xforceplus.tower.data.convert.model;

import java.util.Map;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.model
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-08-21
 *
 * json：需要转化的json
 * rules：map类型，value为表头汉字， key为数据库对应字段
 */
public class JsonToExcelProperty {
    private String json;
    private Map<String ,String> rules;
    private String sheetName;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Map<String, String> getRules() {
        return rules;
    }

    public void setRules(Map<String, String> rules) {
        this.rules = rules;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
