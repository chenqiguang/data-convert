package com.xforceplus.tower.data.convert.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 项目名称: data-convert-sdk
 * 模块名称: com.xforceplus.tower.data.convert
 * 说明:
 * JDK 版本: JDK1.8
 *
 * @author 作者：chenqiguang
 * 创建日期：2019-08-21
 */
@SpringBootTest(classes = TestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("unittest")
public abstract class BaseUnitTest {
}
