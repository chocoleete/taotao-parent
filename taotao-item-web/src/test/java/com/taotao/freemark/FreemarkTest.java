package com.taotao.freemark;

import com.taotao.pojo.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by lee on 2017/3/22.
 */
@SuppressWarnings(value = "all")
public class FreemarkTest {
    /*第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
    * 第二步：设置模板文件所在的路径。
    * 第三步：设置模板文件使用的字符集。一般就是utf-8.
    * 第四步：加载一个模板，创建一个模板对象。
    * 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
    * 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
    * 第七步：调用模板对象的process方法输出文件。
    * 第八步：关闭流。*/
    @Test
    public void testFreemarkFirst() throws IOException, TemplateException {
        //第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        //第二步：设置模板文件所在的路径。
        File dir = new File("F:\\inteliJ_dir\\taotaoclone\\taotao-item-web\\src\\main\\resources\\freemarkTemplate");
        configuration.setDirectoryForTemplateLoading(dir);
        //第三步：设置模板文件使用的字符集。一般就是utf-8.
        configuration.setDefaultEncoding("utf-8");
        //第四步：加载一个模板，创建一个模板对象。
        //Template template = configuration.getTemplate("hello.ftl");
        Template template = configuration.getTemplate("first.htm");

        //第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        Map dataMap = new HashMap<>();
        //dataMap.put("hello", "this is my first freemarkTemplate demo");
        dataMap.put("title", "freemarkDemo");
        dataMap.put("stu", new Student(1,"小明",21,"武汉"));
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1, "小明", 21, "武汉"));
        studentList.add(new Student(2, "小明", 22, "武汉"));
        studentList.add(new Student(3, "小明", 23, "武汉"));
        studentList.add(new Student(4, "小明", 24, "武汉"));
        studentList.add(new Student(5, "小明", 25, "武汉"));
        studentList.add(new Student(6, "小明", 26, "武汉"));
        studentList.add(new Student(7, "小明", 27, "武汉"));
        studentList.add(new Student(8, "小明", 28, "武汉"));
        studentList.add(new Student(9, "小明", 29, "武汉"));
        dataMap.put("studentList", studentList);

        dataMap.put("date", new Date());
        //第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
        //File destinationPath = new File("F:\\uploadtest\\hello.html");
        File destinationPath = new File("F:\\uploadtest\\first.html");
        FileWriter fileWriter = new FileWriter(destinationPath);
        //第七步：调用模板对象的process方法输出文件。
        template.process(dataMap,fileWriter);
        //第八步：关闭流。
        fileWriter.close();
    }

    /*public void testFreemarkFirst() throws IOException, TemplateException {
        //第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        //第二步：设置模板文件所在的路径。
        File dir = new File("F:\\inteliJ_dir\\taotaoclone\\taotao-item-web\\src\\main\\resources\\freemarkTemplate");
        configuration.setDirectoryForTemplateLoading(dir);
        //第三步：设置模板文件使用的字符集。一般就是utf-8.
        configuration.setDefaultEncoding("utf-8");
        //第四步：加载一个模板，创建一个模板对象。
        //Template template = configuration.getTemplate("hello.ftl");
        Template template = configuration.getTemplate("first.htm");

        //第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        Map dataMap = new HashMap<>();
        //dataMap.put("hello", "this is my first freemarkTemplate demo");
        dataMap.put("title", "freemarkDemo");
        dataMap.put("stu", new Student(1,"小明",21,"武汉"));
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1, "小明", 21, "武汉"));
        studentList.add(new Student(2, "小明", 22, "武汉"));
        studentList.add(new Student(3, "小明", 23, "武汉"));
        studentList.add(new Student(4, "小明", 24, "武汉"));
        studentList.add(new Student(5, "小明", 25, "武汉"));
        studentList.add(new Student(6, "小明", 26, "武汉"));
        studentList.add(new Student(7, "小明", 27, "武汉"));
        studentList.add(new Student(8, "小明", 28, "武汉"));
        studentList.add(new Student(9, "小明", 29, "武汉"));
        dataMap.put("studentList", studentList);
        //第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
        //File destinationPath = new File("F:\\uploadtest\\hello.html");
        File destinationPath = new File("F:\\uploadtest\\first.html");
        FileWriter fileWriter = new FileWriter(destinationPath);
        //第七步：调用模板对象的process方法输出文件。
        template.process(dataMap,fileWriter);
        //第八步：关闭流。
        fileWriter.close();
    }*/

    /*public void testFreemarkFirst() throws IOException, TemplateException {
        //第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        //第二步：设置模板文件所在的路径。
        File dir = new File("F:\\inteliJ_dir\\taotaoclone\\taotao-item-web\\src\\main\\resources\\freemarkTemplate");
        configuration.setDirectoryForTemplateLoading(dir);
        //第三步：设置模板文件使用的字符集。一般就是utf-8.
        configuration.setDefaultEncoding("utf-8");
        //第四步：加载一个模板，创建一个模板对象。
        //Template template = configuration.getTemplate("hello.ftl");
        Template template = configuration.getTemplate("first.htm");

        //第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        Map dataMap = new HashMap<>();
        //dataMap.put("hello", "this is my first freemarkTemplate demo");
        dataMap.put("title", "freemarkDemo");
        dataMap.put("stu", new Student(1,"小明",21,"武汉"));
        //第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
        //File destinationPath = new File("F:\\uploadtest\\hello.html");
        File destinationPath = new File("F:\\uploadtest\\first.html");
        FileWriter fileWriter = new FileWriter(destinationPath);
        //第七步：调用模板对象的process方法输出文件。
        template.process(dataMap,fileWriter);
        //第八步：关闭流。
        fileWriter.close();
    }*/
 }
