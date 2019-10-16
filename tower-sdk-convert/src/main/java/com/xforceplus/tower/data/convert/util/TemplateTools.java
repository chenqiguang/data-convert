package com.xforceplus.tower.data.convert.util;

import com.alibaba.fastjson.JSON;
import com.xforceplus.tower.data.convert.client.DataTemplateClient;
import com.xforceplus.tower.data.convert.model.Response;
import com.xforceplus.tower.data.convert.model.TemplateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.util
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-10-15
 */
@Component
public class TemplateTools {
    @Autowired
    private DataTemplateClient dataTemplateClient;

    public TemplateEntity getTemplate(Long tenantId,String templateCode){
        /**获取模版*/
        Response<Map> template = dataTemplateClient.queryTemplate(tenantId, templateCode, 1, 1);
        if (Response.Fail.equals(template.getCode())){
            throw new RuntimeException("查询不到模版"+templateCode);
        }

        Map map = template.getResult();
        if (map==null || map.get("list")==null){
            throw new RuntimeException("根据"+templateCode+"查询不到数据");
        }

        List<TemplateEntity> list = (List<TemplateEntity>) map.get("list");
        if (list.size() == 0){
            throw new RuntimeException("根据"+templateCode+"查询不到数据");
        }
        TemplateEntity entity = JSON.parseObject( JSON.toJSONString(list.get(0)) , TemplateEntity.class);
        return entity;
    }

}
