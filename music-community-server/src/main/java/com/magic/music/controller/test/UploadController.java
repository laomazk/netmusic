package com.magic.music.controller.test;


import com.magic.music.utils.FastDFSUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author laoma
 * @create 2021-02-23 0:31
 */
@RestController
public class UploadController {
    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @PostMapping("/uploadtest")
    public String upload(MultipartFile file){
        String fileId = FastDFSUtils.upload(file);
        String url = nginxHost+fileId;
        /*更新数据库url*/
        return url;
    }

    @PostMapping("/deletest")
    public String delete(){
        String fileId = "group1/M00/00/00/eE8gFmA0ZqqAPIijAAVCl8p5oN8859.jpg";
        boolean delete = FastDFSUtils.delete(fileId);
        // delete 1 失败，0成功？
        System.out.println("delete = " + delete);
        if(delete){
            return "删除成功";
        }
        return "删除失败";
    }
}
