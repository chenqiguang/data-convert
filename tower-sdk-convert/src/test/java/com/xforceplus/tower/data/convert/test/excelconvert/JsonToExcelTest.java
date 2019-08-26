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
                "        \"schoolName\": \"上海大学\",\n" +
                "        \"studentName\": \"张三\",\n" +
                "        \"studentAge\": \"18\",\n" +
                "        \"address\": \"上海\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"schoolName\": \"上海大学\",\n" +
                "        \"studentName\": \"\",\n" +
                "        \"studentAge\": \"18\",\n" +
                "        \"address\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"schoolName\": \"上海大学\",\n" +
                "        \"studentName\": \"张三\",\n" +
                "        \"studentAge\": \"\",\n" +
                "        \"address\": null\n" +
                "    },\n" +
                "    \n" +
                "]";;
        property.setJson(json);

        rules.put("schoolName","学校");
        rules.put("studentName","学生姓名");
        rules.put("studentAge","学生年龄");
        rules.put("addre","地址");
        property.setRules(rules);

        String sheetName = "师生资源";
        property.setSheetName(sheetName);
    }

    @Test
    public void testJsonToExcel(){
        List<JsonToExcelProperty> properties = Lists.newArrayList();
        properties.add(property);
        ExcelConvertUtil.jsonToExcel(properties,"jsonToExcel.xlsx");

//        File file = new File("jsonToExcel.xlsx");
//        if (file.exists()){
//            file.delete();
//        }
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
