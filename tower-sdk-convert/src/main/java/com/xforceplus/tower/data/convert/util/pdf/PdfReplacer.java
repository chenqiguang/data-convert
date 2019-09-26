package com.xforceplus.tower.data.convert.util.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.util.pdf
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-09-26
 * 替换PDF文件某个区域内的文本
 */
public class PdfReplacer {
    private static final Logger logger = LoggerFactory.getLogger(PdfReplacer.class);

    private int fontSize;
    private Map<String, ReplaceRegion> replaceRegionMap = new HashMap();
    private Map<String, Object> replaceTextMap =new HashMap();
    private ByteArrayOutputStream output;
    private PdfReader reader;
    private PdfStamper stamper;
    private PdfContentByte canvas;
    private Font font;


    public PdfReplacer(byte[] pdfBytes) throws DocumentException, IOException{
        init(pdfBytes);
    }

    public PdfReplacer(String fileName) throws IOException, DocumentException {
        FileInputStream in = null;
        try{
            in =new FileInputStream(fileName);
            byte[] pdfBytes = new byte[in.available()];
            in.read(pdfBytes);
            init(pdfBytes);
        }finally{
            in.close();
        }
    }

    public PdfReplacer(FileInputStream in) throws IOException, DocumentException {
        try{
            byte[] pdfBytes = new byte[in.available()];
            in.read(pdfBytes);
            init(pdfBytes);
        }finally{
            in.close();
        }
    }



    private void init(byte[] pdfBytes) throws DocumentException, IOException {
        logger.info("初始化开始");
        reader = new PdfReader(pdfBytes);
        output = new ByteArrayOutputStream();
        stamper = new PdfStamper(reader, output);
        canvas = stamper.getOverContent(1);
        setFont(10);
        logger.info("初始化成功");
    }

    private void close() throws DocumentException, IOException{
        if(reader != null){
            reader.close();
        }
        if(output != null){
            output.close();
        }

        output=null;
        replaceRegionMap=null;
        replaceTextMap=null;
    }

    public void replaceText(float x, float y, float w,float h, String text){
        ReplaceRegion region = new ReplaceRegion(text);
        region.setH(h);
        region.setW(w);
        region.setX(x);
        region.setY(y);
        addReplaceRegion(region);
        this.replaceText(text, text);
    }

    public void replaceText(String name, String text){
        this.replaceTextMap.put(name, text);
    }

    /**
     * 替换文本
     * @throws DocumentException
     * @throws IOException
     */
    private void process() throws DocumentException, IOException{
        try{
            parseReplaceText();
            canvas.saveState();
            Set<Map.Entry<String, ReplaceRegion>> entrys = replaceRegionMap.entrySet();
            for (Map.Entry<String, ReplaceRegion> entry : entrys) {
                ReplaceRegion value = entry.getValue();
                canvas.setColorFill(BaseColor.WHITE);
                canvas.rectangle(value.getX(),value.getY(),value.getW(),value.getH());
            }
            canvas.fill();
            canvas.restoreState();
            //开始写入文本
            canvas.beginText();
            for (Map.Entry<String, ReplaceRegion> entry : entrys) {
                ReplaceRegion value = entry.getValue();
                //设置字体
                canvas.setFontAndSize(font.getBaseFont(), getFontSize());
                /*修正背景与文本的相对位置*/
                canvas.setTextMatrix(value.getX(),value.getY()+2);
                canvas.showText((String) replaceTextMap.get(value.getAliasName()));
            }
            canvas.endText();
        }finally{
            if(stamper != null){
                stamper.close();
            }
        }
    }

    /**
     * 未指定具体的替换位置时，系统自动查找位置
     * @user : caoxu-yiyang@qq.com
     * @date : 2016年11月9日
     */
    private void parseReplaceText() {
        PdfPositionParse parse = new PdfPositionParse(reader);
        Set<Map.Entry<String, Object>> entrys = this.replaceTextMap.entrySet();
        for (Map.Entry<String, Object> entry : entrys) {
            if(this.replaceRegionMap.get(entry.getKey()) == null){
                parse.addFindText(entry.getKey());
            }
        }

        try {
            Map<String, ReplaceRegion> parseResult = parse.parse();
            Set<Map.Entry<String, ReplaceRegion>> parseEntrys = parseResult.entrySet();
            for (Map.Entry<String, ReplaceRegion> entry : parseEntrys) {
                if(entry.getValue() != null){
                    this.replaceRegionMap.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 生成pdf
     * @param fileName
     * @throws DocumentException
     * @throws IOException
     */
    public void toPdf(String fileName) throws DocumentException, IOException{
        FileOutputStream fileOutputStream = null;
        try{
            process();
            fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(output.toByteArray());
            fileOutputStream.flush();
        }catch(IOException e){
            logger.error(e.getMessage(), e);
            throw e;
        }finally{
            if(fileOutputStream != null){
                fileOutputStream.close();
            }
            close();
        }
        logger.info("文件生成成功");
    }

    /**
     * 生成pdf二进制数组
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public byte[] toBytes() throws DocumentException, IOException{
        try{
            process();
            logger.info("二进制数据生成成功");
            return output.toByteArray();
        }finally{
            close();
        }
    }

    /**
     * 添加替换区域
     * @param replaceRegion
     */
    public void addReplaceRegion(ReplaceRegion replaceRegion){
        this.replaceRegionMap.put(replaceRegion.getAliasName(), replaceRegion);
    }

    /**
     * 通过别名得到替换区域
     * @param aliasName
     * @return
     */
    public ReplaceRegion getReplaceRegion(String aliasName){
        return this.replaceRegionMap.get(aliasName);
    }

    public int getFontSize() {
        return fontSize;
    }

    /**
     * 设置字体大小
     * * @param fontSize
     * @throws DocumentException
     * @throws IOException
     */
    public void setFont(int fontSize) throws DocumentException, IOException{
        if(fontSize != this.fontSize){
            this.fontSize = fontSize;
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            font = new Font(bf,this.fontSize,Font.BOLD);
        }
    }

    public void setFont(Font font){
        if(font == null){
            throw new NullPointerException("font is null");
        }
        this.font = font;
    }



}
