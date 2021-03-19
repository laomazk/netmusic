package com.magic.music.service.impl;

import com.magic.music.entity.Song;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.mapper.SongMapper;
import com.magic.music.service.SongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.magic.music.utils.FastDFSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * <p>
 * 歌曲 服务实现类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@Service
@Slf4j
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @Value("${song.default.pic}")
    String defaultSongPic;

    @Transactional
    @Override
    public RespBean updateSongUrl(MultipartFile file, Integer id) {
        String fileId = FastDFSUtils.upload(file);
        String url = nginxHost + fileId;

        if (!file.getOriginalFilename().endsWith(".mp3") && !file.getOriginalFilename().endsWith(".flac")) {
            return RespBean.error("请上传mp3或flac格式的音频文件");
        }

        // 拿到旧的url取出fileId,删掉远程的file
        Song song = super.getById(id);
        if (song != null) {
            String oldUrl = song.getUrl();
            if (oldUrl != null && oldUrl.length() > 0) {

                String oldFileId = oldUrl.substring(nginxHost.length(), url.length());
                FastDFSUtils.delete(oldFileId);
            }
            song.setUrl(url);
            if (super.updateById(song)) {
                return RespBean.ok("更新成功", url);
            }
        }
        return RespBean.error("更新失败");
    }

    @Override
    public ResponseEntity<byte[]> downloadMusic(Integer id) {
        // 拿到歌曲
        Song song = super.getById(id);
        String fileUrl = song.getUrl();
        String name = song.getName();
        int idx = fileUrl.lastIndexOf(".");
        String suff = fileUrl.substring(idx);
        String realName = name + suff;
        // 将远程url转换为输入流
        URL url = null;
        HttpURLConnection conn = null;
        InputStream in = null;
        byte[] bytes = null;
        try {
            url = new URL(fileUrl);
            conn = (HttpURLConnection) url.openConnection();
            //设置超时间为10秒
            conn.setConnectTimeout(10 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 得到输入流
            in = conn.getInputStream();
            log.info("得到输入流");
            bytes = readInputStream(in);
            log.info("转换得到二进制数组完成");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment", new String(realName.getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<byte[]>(bytes,headers, HttpStatus.CREATED);
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
