package com.magic.music.service;

import com.magic.music.entity.Song;
import com.baomidou.mybatisplus.extension.service.IService;
import com.magic.music.entity.vo.RespBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 歌曲 服务类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
public interface SongService extends IService<Song> {
    RespBean updateSongUrl(MultipartFile file, Integer id);

    ResponseEntity<byte[]> downloadMusic(Integer id);
}
