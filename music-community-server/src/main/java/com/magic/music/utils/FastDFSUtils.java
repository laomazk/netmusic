package com.magic.music.utils;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author laoma
 * @create 2021-02-23 0:22
 */
@Slf4j
public class FastDFSUtils {
    private static StorageClient1 client1;


    static {

        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            client1 = new StorageClient1(trackerServer,null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param file
     * @return 文件的访问链接 fileId
     */
    public static String upload(MultipartFile file){
        String oldName = file.getOriginalFilename();
        try {
            return client1.upload_file1(file.getBytes(),oldName.substring(oldName.lastIndexOf(".")+1),null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除已上传的文件
     * @param fileId
     * @return
     */
    public static boolean delete(String fileId){
        try {
            int i = client1.delete_file1(fileId);
            log.info("删除文件成功，返回{}",i);
            return i == 0;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return false;
    }

}
