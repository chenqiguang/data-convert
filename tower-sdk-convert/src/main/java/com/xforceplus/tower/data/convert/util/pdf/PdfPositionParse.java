package com.xforceplus.tower.data.convert.util.pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.util.pdf
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-09-26
 */
public class PdfPositionParse {
    private PdfReader reader;
    private List<String> findText = new ArrayList<String>();
    private PdfReaderContentParser parser;

    public PdfPositionParse(String fileName) throws IOException {
        FileInputStream in = null;
        try{
            in =new FileInputStream(fileName);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            init(bytes);
        }finally{
            in.close();
        }
    }

    public PdfPositionParse(byte[] bytes) throws IOException{
        init(bytes);
    }

    private boolean needClose = true;

    public PdfPositionParse(PdfReader reader){
        this.reader = reader;
        parser = new PdfReaderContentParser(reader);
        needClose = false;
    }

    public void addFindText(String text){
        this.findText.add(text);
    }

    private void init(byte[] bytes) throws IOException {
        reader = new PdfReader(bytes);
        parser = new PdfReaderContentParser(reader);
    }

    /**
     * 解析文本
     * @return
     * @throws IOException
     */
    public Map<String, ReplaceRegion> parse() throws IOException{
        try{
            if(this.findText.size() == 0){
                throw new NullPointerException("没有需要查找的文本");
            }
            PositionRenderListener listener = new PositionRenderListener(this.findText);
            parser.processContent(1, listener);
            return listener.getResult();
        }finally{
            if(reader != null && needClose){
                reader.close();
            }
        }
    }



}
