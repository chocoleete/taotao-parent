package com.taotao.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 2017/3/23.
 */
@SuppressWarnings(value = "all")
@Controller(value = "freemarkerController")
public class FreemarkerController {
    //注入freemarkConfig
    @Resource(name = "freeMarkerConfigurer")
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping(value = "/genHtml")
    @ResponseBody
    public String genHeml() throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        Map map = new HashMap<>();
        map.put("hello", "freemarker整合spring测试");
        File destinationPath = new File("F:\\uploadtest\\springtest.html");
        FileWriter fileWriter = new FileWriter(destinationPath);
        template.process(map, fileWriter);
        fileWriter.close();
        return "ok";
    }
}
