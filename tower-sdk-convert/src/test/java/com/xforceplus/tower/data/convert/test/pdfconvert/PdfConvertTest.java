package com.xforceplus.tower.data.convert.test.pdfconvert;

import com.google.common.collect.Maps;
import com.xforceplus.tower.data.convert.test.BaseUnitTest;
import com.xforceplus.tower.data.convert.util.PdfConvertUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
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

    File pdfTemplate = null;
    @Before
    public void before(){
        try {
            Resource source = new ClassPathResource("pdfTemplate.pdf");
            pdfTemplate = source.getFile();
        }catch (Exception e){
            logger.info("出现异常",e);
        }
    }

    @Test
    public void testGeneratePdf(){
        Map map = Maps.newHashMap();
        map.put("${key1}","value1");
        map.put("${key2}","value2");
        map.put("key3","value3");
        map.put("key4","value4");
        map.put("key5","value5");
        map.put("key6","value6");
        map.put("key7","value7");
        map.put("key8","value8");
        map.put("key9","value9");
        PdfConvertUtil.generatePdf("testPdf.pdf",map,pdfTemplate);

    }

}
