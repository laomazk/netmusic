package com.magic.music;

import com.magic.music.entity.vo.RespBean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author laoma
 * @create 2021-03-07 15:25
 */

public class SongTest {

    @Test
    void checkFormat() {
        String file = "【nin】アスノヨゾラ哨戒班+.mp3";
        // System.out.println("name.endsWith(\".mp3\") = " + name.endsWith(".mp3"));
        if(!file.endsWith(".mp3") || !file.endsWith(".flac")){
            System.out.println("上传歌曲有误");
        }
        if(!file.endsWith(".mp3") && !file.endsWith(".flac")){
            System.out.println("上传歌曲有误222");
        }
    }

    @Test
    void name() {
        System.out.println("this = " + this);
    }
}
