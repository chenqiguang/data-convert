package com.xforceplus.tower.data.convert;

import com.xforceplus.tower.data.convert.util.PdfConvertUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-09-26
 */
public class PdfConvertFactory {

    public static void generatePdf(File pdfTemplate, Map<String, String> data) {
        PdfConvertUtil.generatePdf(pdfTemplate,data);
    }

    public static void generatePdf(FileInputStream inputStream, Map<String, String> data, String pdfName) {
        PdfConvertUtil.generatePdf(inputStream,data,pdfName);
    }

}
