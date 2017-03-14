package com.taotao.fdfs;

import com.taotao.utils.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by lee on 2017/3/11.
 */
@SuppressWarnings(value = "all")
public class TestFastDfs {
    @Test
    public void testUpload() throws IOException, MyException {
        //1.创建一个配置文件fast_dfs.conf,配置文件的内容就是指定TrackerService的地址
        //2.加载配置文件
        ClientGlobal.init("F:\\inteliJ_dir\\taotao-manager-web\\src\\main\\resources\\resource\\fast_dfs.conf");
        //3.创建一个trackerClent对象
        TrackerClient trackerClient = new TrackerClient();
        //4.通过trackerClent对象获得trackerService对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建一个storagerService的引用。null就可以
        StorageServer storageServer = null;
        //6.创建一个storagerClent对象、两个参数 trackerService,storageService
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        /*7.使用storageClient对象上传文件
        * 参数一是文件名，参数二是扩展名不包含“.”，参数三：文件元数据*/
        String[] strings = storageClient.upload_file("G:\\Repositories\\haibao.png", "png", null);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void testFastDfsClient() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("F:\\inteliJ_dir\\taotao-manager-web\\src\\main\\resources\\resource\\fast_dfs.conf");
        String s = fastDFSClient.uploadFile("G:\\迅雷下载\\图片\\未分类\\148514377338089823.jpg");
        System.out.println(s);
    }
}
