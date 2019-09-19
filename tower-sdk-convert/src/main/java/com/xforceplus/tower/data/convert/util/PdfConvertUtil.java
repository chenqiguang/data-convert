package com.xforceplus.tower.data.convert.util;


import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.util
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-09-19
 */
public class PdfConvertUtil {
    private static Logger logger = LoggerFactory.getLogger(PdfConvertUtil.class);


    public static void generatePdf(String pdf, Map<String, Object> data, File pdfTemplate) {
//        PdfReader reader = null;
//        AcroFields s = null;
//        PdfStamper ps = null;
//        ByteArrayOutputStream bos = null;
//
//        try {
//            reader = new PdfReader(pdfTemplate.getPath());
//            bos = new ByteArrayOutputStream();
//            ps = new PdfStamper(reader, bos);
//            s = ps.getAcroFields();
//            // 遍历data 给pdf表单表格赋值
//            for (String key : data.keySet()) {
//                if(data.get(key)!=null) {
//                    s.setField(key, data.get(key).toString());
//                }
//            }
//
//            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
//            ps.setFormFlattening(true);
//            ps.close();
//
//            FileOutputStream fos = new FileOutputStream(pdf);
//
//            fos.write(bos.toByteArray());
//            fos.flush();
//            fos.close();
//        } catch (IOException | DocumentException e) {
//            logger.error("pdf生成：读取文件异常",e);
//        } finally {
//            try {
//                bos.close();
//                reader.close();
//            } catch (IOException e) {
//                logger.error("pdf生成：关闭流异常");
//                e.printStackTrace();
//            }
//        }
    }

    public static void generatePdf1(String pdf, Map<String, Object> data, File pdfTemplate) {
        PDDocument doc = null;

        try {
           doc = PDDocument.load(pdfTemplate);
           //PDFTextStripper stripper=new PDFTextStripper("ISO-8859-1");
            List pages = doc.getDocumentCatalog().getAllPages();
            for( int i=0; i<pages.size(); i++ ) {
                 PDPage page = (PDPage)pages.get( i );
                 PDStream contents = page.getContents();
                 PDFStreamParser parser = new PDFStreamParser(contents.getStream() );
                 parser.parse();
                 List tokens = parser.getTokens();
               for (int j = 0; j < tokens.size(); j++) {
                   Object next = tokens.get(j);
                   if (next instanceof PDFOperator) {
                       PDFOperator op = (PDFOperator) next;
                       if (op.getOperation()!=null && data.get(op.getOperation())!=null){
                           COSString previous = (COSString) tokens.get(j - 1);
                           String string = previous.getString();
                           string = string.replaceFirst(strToFind, message);

                           previous.reset();
                           previous.append(string.getBytes("GBK"));
                       }

                   }
               }
               //now that the tokens are updated we will replace the
               //page content stream.
               PDStream updatedStream = new PDStream(doc);
               OutputStream out = updatedStream.createOutputStream();
               ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
               tokenWriter.writeTokens(tokens);
               page.setContents(updatedStream);
           }
           doc.save(pdf);
        }catch (Exception e){

        } finally {
            if (doc != null) {
                try {
                    doc.close();
                } catch (IOException e) {
                    logger.error("close doc error",e);
                }
            }
        }
    }


}
