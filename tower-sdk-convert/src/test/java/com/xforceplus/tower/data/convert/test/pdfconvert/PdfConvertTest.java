package com.xforceplus.tower.data.convert.test.pdfconvert;

import com.google.common.collect.Maps;
import com.xforceplus.tower.data.convert.test.BaseUnitTest;
import com.xforceplus.tower.data.convert.util.PdfConvertUtil;
import com.xforceplus.tower.data.convert.util.pdf.PdfReplacer;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.test.pdfconvert
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-09-19
 */
public class PdfConvertTest extends BaseUnitTest {
    private static Logger logger = LoggerFactory.getLogger(PdfConvertTest.class);

    @Autowired
    private PdfConvertUtil pdfConvertUtill;

    File pdfTemplate = null;
    FileInputStream inputStream = null;
    Map<String ,String> map = Maps.newHashMap();
    @Before
    public void before(){
        try {
            Resource source = new ClassPathResource("pdfTemplate.pdf");
            pdfTemplate = source.getFile();


            inputStream = new FileInputStream(source.getFile());

            map.put("${key1}","小白");
            map.put("${key2}","社会大学");
            map.put("${key3}","15112345678");
            map.put("${key4}","哈哈哈哈");
        }catch (Exception e){
            logger.info("出现异常",e);
        }
    }

    @Test
    public void testPdfReplacer(){
        try {
            PdfConvertUtil.generatePdf(pdfTemplate,map);
        }catch (Exception e){
            logger.error("",e);
        }

    }

    @Test
    public void testPdfReplacerInputStream(){
        try {
            PdfConvertUtil.generatePdf(inputStream,map,"testInputStram.pdf");
        }catch (Exception e){
            logger.error("",e);
        }

    }

    @Test
    public void testGeneratePdfTemplate(){

        pdfConvertUtill.generatePdf(0L,"template-2",map,"testTemplateInputStram.pdf");

    }


}
