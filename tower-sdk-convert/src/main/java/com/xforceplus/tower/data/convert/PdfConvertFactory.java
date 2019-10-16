package com.xforceplus.tower.data.convert;

import com.xforceplus.tower.data.convert.util.PdfConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class PdfConvertFactory {
    @Autowired
    private PdfConvertUtil pdfConvertUtil;

    public static void generatePdf(File pdfTemplate, Map<String, String> data) {
        PdfConvertUtil.generatePdf(pdfTemplate,data);
    }

    public static void generatePdf(FileInputStream inputStream, Map<String, String> data, String pdfName) {
        PdfConvertUtil.generatePdf(inputStream,data,pdfName);
    }

    public void generatePdf(Long tenantId,String templateCode,Map<String, String> data,String pdfName){
        pdfConvertUtil.generatePdf(tenantId, templateCode, data, pdfName);
    }

}
