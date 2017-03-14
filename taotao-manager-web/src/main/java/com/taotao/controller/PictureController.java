package com.taotao.controller;

import com.taotao.commom.utils.JsonUtils;
import com.taotao.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传Controller
 * Created by lee on 2017/3/11.
 */
@SuppressWarnings(value = "all")
@Controller(value = "pictureController")
public class PictureController {

    @Value(value = "${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    //解决ie浏览器等不兼容的问题
    @RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    @ResponseBody
    public String fileUpload(MultipartFile uploadFile) {
        try {
            //1获取文件名
            String originalFilename = uploadFile.getOriginalFilename();//获得文件名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);//获得文件扩展名，不带“.”
            //2创建一个fastDfs的客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/fast_dfs.conf");
            //3执行上传处理
            String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            String url = IMAGE_SERVER_URL + path;
            //4返回map
            Map result = new HashMap<>();
            result.put("error", 0);
            result.put("url", url);
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            //5返回map
            Map result = new HashMap<>();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return JsonUtils.objectToJson(result);
        }
    }

    /*
    @Value(value = "${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping(value = "/pic/upload")
    @ResponseBody
    public Map fileUpload(MultipartFile uploadFile) {
        try {
            //1获取文件名
            String originalFilename = uploadFile.getOriginalFilename();//获得文件名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);//获得文件扩展名，不带“.”
            //2创建一个fastDfs的客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/fast_dfs.conf");
            //3执行上传处理
            String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            String url = IMAGE_SERVER_URL + path;
            //4返回map
            Map result = new HashMap<>();
            result.put("error", 0);
            result.put("url", url);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //5返回map
            Map result = new HashMap<>();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return result;
        }
    }*/
}
