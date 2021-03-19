package com.magic.music;

import com.magic.music.utils.FastDFSUtils;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author laoma
 * @create 2021-02-21 11:57
 */
@Slf4j
// @SpringBootTest
public class FastdfsTest {

    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    /**
     * http://120.79.32.22/group1/M00/00/00/eE8gFmAzpF2AQdUXAAA0GwfqpaQ335.jpg 默认头像路径
     */
    @Test
    void testUpload(){
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            NameValuePair nvp[] = null;
            //上传到文件系统
            String fileId = client.upload_file1("H:\\毕业设计\\fdfs\\轮播图\\8.jpg", "jpg",
                    nvp);
            String url = "http://120.79.32.22/";
            log.info(url+fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDownload() {
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            byte[] bytes = client.download_file1("group1/M00/00/00/eE8gFmAx3LSAX7mjAACa6fBFNHs080.jpg");
            FileOutputStream fos = new FileOutputStream(new File("H:\\毕业设计\\fdfs\\2.jpg"));
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // http://120.79.32.22/group1/M00/00/00/eE8gFmAzpF2AQdUXAAA0GwfqpaQ335.jpg  要切掉前面的
    @Test
    void testDelete0() {
        String fileId = "http://120.79.32.22/group1/M00/00/00/eE8gFmAzpF2AQdUXAAA0GwfqpaQ335.jpg";
        boolean delete = FastDFSUtils.delete(fileId);
        System.out.println("delete = " + delete);
    }

    @Test
    void testDelete(){
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);

            int i = client.delete_file1("group1/M00/00/00/eE8gFmAzH4mAFgdjAAVCl8p5oN8083.jpg");
            log.info("删除成功[{}]",i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //todo
    @Test
    void testSubString() {
        String url = "http://120.79.32.22/group1/M00/00/00/eE8gFmAzpF2AQdUXAAA0GwfqpaQ335.jpg";
        String substring = url.substring(nginxHost.length() , url.length());
        System.out.println("substring = " + substring);
    }
}
