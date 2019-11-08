package com.xforceplus.tower.data.convert.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ObjectUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.xforceplus.tower.data.convert.exception.ExcelToJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final String rgex = "\\$\\{(.*?)}";

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
                logger.error("errorjson:{}",tempJson);
                throw new ExcelToJsonException("convert fail , please check fields "+ JSON.toJSONString(getErrorSub(tempJson,rgex)));
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
            String key = new StringBuilder().append("${").append(excelHeaders.get(i).trim()).append("}").toString();
            String value = values.get(i) == null ? "" : values.get(i);
            json = json.replace(key,value);
        }
        return json;
    }

    /**
     * 截取转换失败的字段
     * @param json
     * @param rgex
     * @return
     */
    private List<String> getErrorSub(String json,String rgex){
        Pattern pattern = Pattern.compile(rgex);
        Matcher matcher = pattern.matcher(json);

        List<String> list = Lists.newArrayList();
        while (matcher.find()){
            int i =1;
            list.add(matcher.group(i));
            i++;
        }
        return list;
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
