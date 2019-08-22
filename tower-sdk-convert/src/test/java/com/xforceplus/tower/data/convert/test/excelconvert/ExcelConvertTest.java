package com.xforceplus.tower.data.convert.test.excelconvert;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.*;
import com.google.common.collect.Lists;
import com.xforceplus.tower.data.convert.test.BaseUnitTest;
import com.xforceplus.tower.data.convert.util.ExcelConvertUtil;
import com.xforceplus.tower.data.convert.model.ExcelToJsonProperty;
import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.excelconvert
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-08-21
 */
public class ExcelConvertTest extends BaseUnitTest {
    private static Logger logger = LoggerFactory.getLogger(ExcelConvertUtil.class);

    private File excel = null;
    private MultipartFile multipartFile = null;
    @Before
    public void before(){
        Resource resource = new ClassPathResource("excelToJson.xlsx");
        try {
            excel =  resource.getFile();
            FileInputStream fileInputStream = new FileInputStream(excel);
            multipartFile =
                    new MockMultipartFile("file",excel.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);

        } catch (IOException e) {
            logger.error("get file error :",e);
        }

    }

    @Test
    public void testExcelToJson(){
        ExcelToJsonProperty property = new ExcelToJsonProperty();
        property.setFile(multipartFile);
        String json = "{\n" +
                "\"school\":\n" +
                " {\n" +
                "   \"schoolName\":\"${学校}\",\n" +
                "   \"student\":\n" +
                "    {\n" +
                "     \"studentName\":\"${学生姓名}\",\n" +
                "     \"studentAge\":\"${学生年龄}\"\n" +
                "    }\n" +
                " }\n" +
                "}";
        property.setJson(json);
        property.setStartRow(1);
        property.setStartSheet(1);

        ExcelConvertUtil.ExcelToJson(property);
    }

}