package com.zx.Controller;

import com.zx.Pojo.user;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auother zhangxing
 * @Date 2019-05-23 14:37
 * @note
 */
@Api("测试Controller")
@RestController
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    @ApiOperation(value = "测试方法",notes = "this is note",response = String.class,httpMethod = "GET")
    @RequestMapping("/hello")
    public String sayHello(){
        logger.info("hello world");
        return "hello world";
    }

    @ApiOperation(value = "返回对象信息",notes = "this is note",response = String.class,httpMethod = "GET")
    @RequestMapping("/backUserinfo")
    public user backUserinfo(){
        return new user("zhangxing");
    }
}
