package com.xforceplus.tower.data.convert.client;

import com.xforceplus.tower.data.convert.api.DataTemplateApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert.client
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-10-15
 */
@FeignClient(name = "data-convert-service", url = "${data-convert-service}")
public interface DataTemplateClient extends DataTemplateApi {
}
