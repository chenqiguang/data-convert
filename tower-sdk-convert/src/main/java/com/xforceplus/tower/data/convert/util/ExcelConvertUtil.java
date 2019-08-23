package com.xforceplus.tower.data.convert.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ObjectUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xforceplus.tower.data.convert.exception.ExcelToJsonException;
import com.xforceplus.tower.data.convert.listener.ExcelConvertListener;
import com.xforceplus.tower.data.convert.model.ExcelToJsonProperty;
import com.xforceplus.tower.data.convert.model.JsonToExcelProperty;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-08-20
 */
@Component
public class ExcelConvertUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelConvertUtil.class);

    /**
     *
     * @param property excel 转化为 json 的请求参数
     */
    public static List<String> ExcelToJson(ExcelToJsonProperty property){
        checkProperties(property);
        MultipartFile file = property.getFile();
        String json = property.getJson();
        Integer startRow = property.getStartRow();
        Integer startSheet = property.getStartSheet();

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();

            ExcelConvertListener excelListener = new ExcelConvertListener();
            excelListener.setJson(json);

            ExcelReader excelReader = EasyExcelFactory.getReader(inputStream,excelListener);
            List<Sheet> sheets = excelReader.getSheets();
            logger.info("all sheets {}",sheets);
            if (ObjectUtils.isEmpty(sheets)){
                throw new ExcelToJsonException("current excel has no sheet,please import a right excel!");
            }

            List<String> datas = Lists.newArrayList();
            for (int i = startSheet-1;i<sheets.size();i++){
                Sheet sheet = sheets.get(i);
                sheet.setHeadLineMun(startRow-1);
                excelReader.read(sheet);

                List<String> data = excelListener.getData();
                datas.addAll(data);
            }

            return datas;
        }catch (ExcelToJsonException | IOException e){
            logger.error("excelToJson exception",e);
        } finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("excelToJson Io Exception ",e);
                }
            }
        }
        return null;
    }


    /**
     * json 转化为excel
     * @param properties
     */
    public static void jsonToExcel(List<JsonToExcelProperty> properties,String fileName){
        checkJsonToExcelProperty(properties,fileName);
        OutputStream out = null;
        ExcelWriter writer = null;
        try {
            out = new FileOutputStream(fileName);
            writer = EasyExcelFactory.getWriter(out);
            for (int i=0;i<properties.size();i++){
                JsonToExcelProperty property = properties.get(i);

                String sheetName = ObjectUtils.isEmpty(property.getSheetName()) ? "sheet"+(i++) :property.getSheetName();
                String json = property.getJson();
                Map<String, String> rules = property.getRules();
                List<List<String>> headers = createTestListStringHead(json,rules);

                Sheet sheet = new Sheet(i+1,0);
                sheet.setSheetNo(i+1);
                sheet.setSheetName(sheetName);
                sheet.setHead(headers);

                TableStyle tableStyle = new TableStyle();
                tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE);
                sheet.setTableStyle(tableStyle);

                List datas = createTestListObject(json,rules);
                writer.write1(datas,sheet);

            }
        }catch (Exception e){
            logger.error("jsonToExcel error :" ,e);
        }finally {
            if (writer!=null){
                writer.finish();
            }
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("outinputstream close error ",e);
                }
            }

        }

    }

    /**
     * 获取要生成excel的数据列表
     * @param json
     */
    private static List<List<Object>> createTestListObject(String json,Map<String, String> rules) {
        List<Map> maps = JSONObject.parseArray(json, Map.class);
        List<List<Object>> datas = Lists.newArrayList();
        for (Map<String ,String> map:maps){
            List<String> keys = new ArrayList(map.keySet());
            List<String> ruleKeys = new ArrayList(rules.keySet());

            List<Object> values = Lists.newArrayList();
            keys.stream().forEach(k->{
                if (ruleKeys.contains(k)){
                    String value = map.get(k);
                    if (ObjectUtils.isEmpty(value)){
                        value = "";
                    }
                    values.add(value);
                }
            });
            datas.add(values);
        }

        return datas;
    }

    /**
     * 获取生成Excel的表头
     * @param rules
     */
    private static List<List<String>> createTestListStringHead(String json, Map<String, String> rules) {
        List<Map> datas = JSONObject.parseArray(json, Map.class);
        Map<String,String> map = datas.get(0);
        List<List<String>> headers = Lists.newArrayList();
        for (String key: map.keySet()){
            List<String> header = Lists.newArrayList();
            String chinessHeader = rules.get(key);
            if (ObjectUtils.isEmpty(chinessHeader)){
                continue;
            }
            header.add(chinessHeader);
            headers.add(header);
        }
        return headers;
    }


    /**
     * 校验参数
     * @param propertys
     */
    private static void checkJsonToExcelProperty(List<JsonToExcelProperty>  propertys,String fileName) {
        if (ObjectUtils.isEmpty(propertys)){
            throw new ExcelToJsonException("properties can not be empty!");
        }
        for (JsonToExcelProperty property:propertys){
            String json = property.getJson();
            Map<String, String> rules = property.getRules();

            Objects.requireNonNull(rules,"rules can not be null!");
            if (ObjectUtils.isEmpty(json)){
                throw new ExcelToJsonException("json can not be null!");
            }
        }

        Objects.requireNonNull(fileName,"fileName can not be null!");

        if (!fileName.endsWith(ExcelTypeEnum.XLS.getValue()) && !fileName.endsWith(ExcelTypeEnum.XLSX.getValue())){
            throw new ExcelToJsonException("excel must endwith .xls or .xlsx!");
        }
    }


    /**
     * 校验参数
     * @param property
     */
    private static void checkProperties(ExcelToJsonProperty property) {
        MultipartFile file = property.getFile();
        String json = property.getJson();
        String filename = file.getOriginalFilename();
        Integer startRow = property.getStartRow();
        Integer startSheet = property.getStartSheet();

        Objects.requireNonNull(file,"file can not be null!");
        Objects.requireNonNull(filename,"fileName can not be null!");

        if (ObjectUtils.isEmpty(json)){
            throw new ExcelToJsonException("the result json can not be null!");
        }
        if (!filename.endsWith(ExcelTypeEnum.XLS.getValue()) && !filename.endsWith(ExcelTypeEnum.XLSX.getValue())){
            throw new ExcelToJsonException("excel must endwith .xls or .xlsx!");
        }
        if (startRow<=0 || startSheet <=0){
            throw new ExcelToJsonException("开始的行startRow 或者 开始读取的sheet 最小要从 1 开始!");
        }

    }







}
