package com.xforceplus.tower.data.convert.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.model
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-08-20
 *
 *   file 所要解析的文件
 *   json 最后所要的到的json
 *   startSheet 开始从那个sheet读取数据，默认是第一个
 *   startRow 开始从那行读取数据，默认第一行
 */
public class ExcelToJsonProperty {
    private MultipartFile file;
    private String json;
    private Integer startSheet = 1;
    private Integer startRow = 1;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Integer getStartSheet() {
        return startSheet;
    }

    public void setStartSheet(Integer startSheet) {
        this.startSheet = startSheet;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    @Override
    public String toString() {
        return "ExcelToJsonProperty{" +
                "file=" + file +
                ", json='" + json + '\'' +
                ", startSheet=" + startSheet +
                ", startRow=" + startRow +
                '}';
    }
}
