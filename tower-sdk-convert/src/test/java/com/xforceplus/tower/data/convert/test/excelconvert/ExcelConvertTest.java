package com.xforceplus.tower.data.convert.test.excelconvert;


import com.xforceplus.tower.data.convert.test.BaseUnitTest;
import com.xforceplus.tower.data.convert.util.ExcelConvertUtil;
import com.xforceplus.tower.data.convert.model.ExcelToJsonProperty;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.List;
import static org.springframework.util.Assert.*;
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

    private MultipartFile multipartFile = null;
    private MultipartFile multipartFile1 = null;
    @Before
    public void before(){
        Resource resource = new ClassPathResource("excelToJson.xlsx");
        Resource resource1 = new ClassPathResource("excelToJsonNoData.xlsx");
        try {
            File excel =  resource.getFile();
            FileInputStream fileInputStream = new FileInputStream(excel);
            multipartFile =
                    new MockMultipartFile("file",excel.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);

            File excel1 = resource1.getFile();
            FileInputStream fileInputStream1 = new FileInputStream(excel1);
            multipartFile1 =
                    new MockMultipartFile("file1",excel1.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream1);
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

        ExcelConvertUtil.excelToJson(property);
    }

    @Test
    public void testExcelToJsonDefualt(){
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
//        property.setStartRow(1);
//        property.setStartSheet(1);

        String datas = ExcelConvertUtil.excelToJson(property);
        notNull(datas,"convert excel fail!");
    }

    @Test
    public void testExcelToJsonWrongStartRow(){
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
        property.setStartRow(2);
//        property.setStartSheet(1);
        try {
            ExcelConvertUtil.excelToJson(property);
        }catch (Exception e){
            Assert.assertEquals("convert fail ,please check your json and excel header is all right , or check your startRow is right for your excel!",e.getCause().getMessage());
        }
    }
    @Test
    public void testExcelToJsonNoData(){
        ExcelToJsonProperty property = new ExcelToJsonProperty();
        property.setFile(multipartFile1);
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

        String datas = ExcelConvertUtil.excelToJson(property);
//        Assert.assertNull(datas);
    }
}
