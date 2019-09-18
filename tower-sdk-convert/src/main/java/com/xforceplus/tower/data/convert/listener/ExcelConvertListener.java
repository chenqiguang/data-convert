package com.xforceplus.tower.data.convert.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ObjectUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xforceplus.tower.data.convert.exception.ExcelToJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.listener
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-08-20
 */
public class ExcelConvertListener extends AnalysisEventListener {
    private static Logger logger = LoggerFactory.getLogger(ExcelConvertListener.class);
    private static final String replace_key1 = "${";
    private static final String replace_key2 = "}";

    private List<Map> data = Lists.newArrayList();
    private String json;
    private List<String> excelHeaders = Lists.newArrayList();

    @Override
    public void invoke(Object object, AnalysisContext context) {
        if (ObjectUtils.isEmpty(excelHeaders)){
            excelHeaders.addAll((ArrayList<String>)object);
        }else {
            ArrayList<String> values = (ArrayList<String>) object;
            if (values.size() < excelHeaders.size()){
                int size = excelHeaders.size()-values.size();
                for (int i=0;i<size;i++){
                    values.add("");
                }
            }
            String tempJson = replaceJson(json,excelHeaders,values);
            if (tempJson.contains(replace_key1) && tempJson.contains(replace_key2)){
                logger.info("excelHeaders:{}",excelHeaders);
                logger.info("jsonTemplate:{}",json);
                throw new ExcelToJsonException("convert fail ,please check your json and excel header is all right , or check your startRow is right for your excel!");
            }
            Map map = JSON.parseObject(tempJson, Map.class);
            data.add(map);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        logger.info("doAfterAllAnalysed");
    }

    /**
     * replace excel data to json data
     * @param json
     * @param excelHeaders
     */
    private String replaceJson(String json, List<String> excelHeaders,ArrayList<String> values) {
        for (int i=0;i<excelHeaders.size();i++){
            String key = new StringBuilder().append("${").append(excelHeaders.get(i)).append("}").toString();
            String value = values.get(i) == null ? "" : values.get(i);
            json = json.replace(key,value);
        }
        return json;
    }

    public List<Map> getData() {
        return data;
    }

    public void setData(List<Map> data) {
        this.data = data;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
