package com.pinyougou.shop.controller;

import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.FastDFSClient;


/**
 * 文件上传Controller
 */
@RestController
public class UploadController {

    @Value("${FILE_SERVER_URL}") //注入配置文件中的服务器的IP地址
    private String file_service_url;//文件服务器地址

    @RequestMapping("/upload")
    public Result upload(MultipartFile file) {
        //1.获取文件的扩展名
        String originalFilename = file.getOriginalFilename();
        //截取最后一个.之后的字符,获取文件的后缀名
        String exName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {
            //2.创建一个FastDFS的客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");

            //3.执行上传处理    //参数1:上传文件的字节流文件. 参数2:上传文件的后缀名
            String path = fastDFSClient.uploadFile(file.getBytes(), exName);

            //4.拼接返回的url和ip地址,拼接成完成的url
            String url = file_service_url+ path;
            //上传成功,返回给客户端url地址
            return new Result(true,url);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,"上传失败");
        }
    }




}
