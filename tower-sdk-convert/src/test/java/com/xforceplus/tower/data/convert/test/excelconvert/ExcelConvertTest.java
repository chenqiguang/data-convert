package com.xforceplus.tower.data.convert.test.excelconvert;


import com.alibaba.fastjson.JSONObject;
import com.xforceplus.tower.data.convert.test.BaseUnitTest;
import com.xforceplus.tower.data.convert.test.testmodel.UserAddRequest;
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

    @Test
    public void testCompany(){
        MultipartFile usrFile = null;
        Resource source = new ClassPathResource("company.xlsx");
        try {
            File excel =  source.getFile();
            FileInputStream fileInputStream = new FileInputStream(excel);
            usrFile =
                    new MockMultipartFile("file",excel.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        }catch (Exception e){
            logger.error("error",e);
            return;
        }
        ExcelToJsonProperty property = new ExcelToJsonProperty();
        property.setFile(usrFile);
        String json = "{\"ceQuota\":\"${增值税电子普通发票限额}\",\"proxyManagerIdCardTimeLong\":\"${代理人身份证长期}\",\"locationAddr\":\"${企业经营详细地址}\",\"locationCity\":\"${企业经营所在市区}\",\"businessStartTime\":\"${营业期限开始时间}\",\"managerLocation\":\"${法人归属地}\",\"managerIdCardTimeLong\":\"${法人身份证长期}\",\"platManagerStatus\":\"${平台管理人身份}\",\"proxyManagerIdCardEndTime\":\"${代理人证件结束时间}\",\"traditionAuthenFlag\":\"${传统认证管理状态}\",\"businessScope\":\"${企业经营范围}\",\"cquota\":\"${增值税普通发票限额}\",\"bankCity\":\"${银行所在市区}\",\"registLocationAddr\":\"${企业注册详细地址}\",\"inspectionServiceFlag\":\"${查验服务状态}\",\"proxyManagerPhone\":\"${代理人联系方式}\",\"registLocationCity\":\"${企业注册城市}\",\"taxpayerQualificationType\":\"${纳税人资质类型}\",\"bankNo\":\"${对公银行账户}\",\"taxNum\":\"${税号}\",\"proxyManagerName\":\"${代理人姓名}\",\"locationArea\":\"${企业经营所在省份}\",\"proxyManagerCardType\":\"${代理人证据类型}\",\"managerIdCardStartTime\":\"${法人证件开始时间}\",\"managerCardType\":\"${法人证件类型}\",\"managerIdCard\":\"${法人身份证}\",\"speedInspectionChannelFlag\":\"${极速查验通道状态}\",\"managerPhone\":\"${法人联系方式}\",\"squota\":\"${增值税专用发票限额}\",\"proxyManagerIdCardStartTime\":\"${代理人证件开始时间}\",\"registLocationArea\":\"${企业注册省份}\",\"bankName\":\"${开户行名称}\",\"juQuota\":\"${增值税普通发票-卷票限额}\",\"businessEndTime\":\"${营业期限结束时间}\",\"managerName\":\"${法人姓名}\",\"bankArea\":\"${银行所在省份}\",\"managerIdCardEndTime\":\"${法人证件结束时间}\",\"proxyManagerIdCard\":\"${代理人身份证}\",\"businessTimeLong\":\"${营业时间长期}\",\"companyName\":\"${企业名称}\",\"bankBranchName\":\"${开户银行支行名称}\"}";
        property.setJson(json);
        String datas = ExcelConvertUtil.excelToJson(property);

        List<String> users = JSONObject.parseArray(datas, String.class);
        logger.info(users.toString());
    }

    @Test
    public void testUser(){
        MultipartFile usrFile = null;
        Resource source = new ClassPathResource("导入人员.xlsx");
        try {
            File excel =  source.getFile();
            FileInputStream fileInputStream = new FileInputStream(excel);
            usrFile =
                    new MockMultipartFile("file",excel.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        }catch (Exception e){
            logger.error("error",e);
            return;
        }
        ExcelToJsonProperty property = new ExcelToJsonProperty();
        property.setFile(usrFile);
        String json = "{\"userSex\":\"${人员性别 }\",\"userWorkTel\":\"${办公电话}\",\"contactAddr\":\"${联系地址}\",\"userPhone\":\"${手机号码}\",\"accountNum\":\"${登陆账号}\",\"userEmailAddr\":\"${邮箱地址}\",\"userIdCard\":\"${身份证号码}\",\"userCode\":\"${人员代码}\",\"userName\":\"${人员姓名}\",\"userNumber\":\"${人员工号}\"}";
        property.setJson(json);
        String datas = ExcelConvertUtil.excelToJson(property);

        List<String> users = JSONObject.parseArray(datas, String.class);
        logger.info(users.toString());
    }


}
