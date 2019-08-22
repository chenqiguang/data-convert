package com.xforceplus.tower.data.convert.test.excelconvert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xforceplus.tower.data.convert.model.JsonToExcelProperty;
import com.xforceplus.tower.data.convert.test.BaseUnitTest;
import com.xforceplus.tower.data.convert.util.ExcelConvertUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.test.excelconvert
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-08-22
 */
public class JsonToExcelTest extends BaseUnitTest {

    private String json;
    private Map<String ,String> rules = Maps.newHashMap();
    private JsonToExcelProperty property;
    @Before
    public void before(){
        property = new JsonToExcelProperty();
        json = "[\n" +
                "    {\n" +
                "        \"cellStyleMap\": {\n" +
                "            \n" +
                "        },\n" +
                "        \"schoolName\": \"学校0\",\n" +
                "        \"studentAge\": 18,\n" +
                "        \"studentName\": \"姓名0\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"cellStyleMap\": {\n" +
                "            \n" +
                "        },\n" +
                "        \"schoolName\": \"学校1\",\n" +
                "        \"studentAge\": 19,\n" +
                "        \"studentName\": \"姓名1\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"cellStyleMap\": {\n" +
                "            \n" +
                "        },\n" +
                "        \"schoolName\": \"学校2\",\n" +
                "        \"studentAge\": 20,\n" +
                "        \"studentName\": \"姓名2\"\n" +
                "    }\n" +
                "]";;
        property.setJson(json);

        rules.put("schoolName","学校");
        rules.put("studentName","学生姓名");
        rules.put("studentAge","学生年龄");
        property.setRules(rules);

        String sheetName = "师生资源";
        property.setSheetName(sheetName);
    }

    @Test
    public void testJsonToExcel(){
        List<JsonToExcelProperty> properties = Lists.newArrayList();
        properties.add(property);
        ExcelConvertUtil.jsonToExcel(properties,"jsonToExcel.xlsx");

        File file = new File("jsonToExcel.xlsx");
        if (file.exists()){
            file.delete();
        }
    }

    @Test
    public void testJsonToExcel2Sheet(){
        List<JsonToExcelProperty> properties = Lists.newArrayList();
        properties.add(property);

        JsonToExcelProperty property1 = new JsonToExcelProperty();
        property1.setJson(json);
        property1.setSheetName("师生资源1");
        property1.setRules(rules);
        properties.add(property1);

        ExcelConvertUtil.jsonToExcel(properties,"jsonToExcel.xlsx");

        File file = new File("jsonToExcel.xlsx");
        if (file.exists()){
            file.delete();
        }
    }


}
