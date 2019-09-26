package com.xforceplus.tower.data.convert.util;

import com.alibaba.excel.util.ObjectUtils;
import com.xforceplus.tower.data.convert.util.pdf.PdfReplacer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.util
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-09-19
 */
public class PdfConvertUtil {
    private static Logger logger = LoggerFactory.getLogger(PdfConvertUtil.class);

    /**
     * 生成pdf
     * @param pdfTemplate
     * @param data
     */
    public static void generatePdf(File pdfTemplate,Map<String, String> data) {
        try {
            Objects.requireNonNull(pdfTemplate,"file pdfTemplate can not be null");
            Objects.requireNonNull(data,"replace data can not be null");

            PdfReplacer textReplacer = new PdfReplacer(pdfTemplate.getPath());
            Set<String> keys = data.keySet();
            for(String key:keys){
                textReplacer.replaceText(key,String.valueOf(data.get(key)));
            }
            textReplacer.toPdf(pdfTemplate.getName());
        }catch (Exception e){
            logger.error("",e);
        }
    }

    /**
     * 根据输入流生成pdf
     * @param inputStream
     * @param data
     * @param pdfName
     */
    public static void generatePdf(FileInputStream inputStream,Map<String, String> data,String pdfName) {
        try {
            final String name = "default.pdf";
            Objects.requireNonNull(inputStream,"file inputStream can not be null");
            Objects.requireNonNull(data,"replace data can not be null");
            if (ObjectUtils.isEmpty(pdfName)){pdfName = name;}
            PdfReplacer textReplacer = new PdfReplacer(inputStream);
            Set<String> keys = data.keySet();
            for(String key:keys){
                textReplacer.replaceText(key,String.valueOf(data.get(key)));
            }
            textReplacer.toPdf(pdfName);
        }catch (Exception e){
            logger.error("",e);
        }
    }



}
