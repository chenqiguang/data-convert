package com.xforceplus.tower.data.convert.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ObjectUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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

    private List<String> data = Lists.newArrayList();
    private String json;
    private List<String> excelHeaders = Lists.newArrayList();

    @Override
    public void invoke(Object object, AnalysisContext context) {
        if (ObjectUtils.isEmpty(excelHeaders)){
            excelHeaders.addAll((ArrayList<String>)object);
        }else {
            String tempJson = replaceJson(json,excelHeaders,object);
            data.add(tempJson);
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
    private String replaceJson(String json, List<String> excelHeaders,Object object) {
        ArrayList<String> values = (ArrayList<String>) object;
        for (int i=0;i<excelHeaders.size();i++){
            String key = new StringBuilder().append("${").append(excelHeaders.get(i)).append("}").toString();
            String value = values.get(i);
            json = json.replace(key,value);
        }
        return json;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
