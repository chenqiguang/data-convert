package com.xforceplus.tower.data.convert.util.pdf;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.util.HashMap;
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
public class PositionRenderListener implements RenderListener {
    private List<String> findText;
    /**出现无法取到值的情况，默认为12*/
    private float defaultH;
    /**可能出现无法完全覆盖的情况，提供修正的参数，默认为2*/
    private float fixHeight;
    public PositionRenderListener(List<String> findText, float defaultH,float fixHeight) {
        this.findText = findText;
        this.defaultH = defaultH;
        this.fixHeight = fixHeight;
    }

    public PositionRenderListener(List<String> findText) {
        this.findText = findText;
        this.defaultH = 12;
        this.fixHeight = 2;
    }

    @Override
    public void beginTextBlock() {

    }

    @Override
    public void endTextBlock() {

    }

    @Override
    public void renderImage(ImageRenderInfo imageInfo) {
    }

    private Map<String, ReplaceRegion> result = new HashMap<String, ReplaceRegion>();
    @Override
    public void renderText(TextRenderInfo textInfo) {
        String text = textInfo.getText();
        for (String keyWord : findText) {
            if (null != text && text.equals(keyWord)){
                Rectangle2D.Float bound = textInfo.getBaseline().getBoundingRectange();
                ReplaceRegion region = new ReplaceRegion(keyWord);
                region.setH(bound.height == 0 ? defaultH : bound.height);
                region.setW(bound.width);
                region.setX(bound.x);
                region.setY(bound.y-this.fixHeight);
                result.put(keyWord, region);
            }
        }
    }

    public Map<String, ReplaceRegion> getResult() {
        for (String key : findText) {
            if(this.result.get(key) == null){
                this.result.put(key, null);
            }
        }
        return this.result;
    }


}
