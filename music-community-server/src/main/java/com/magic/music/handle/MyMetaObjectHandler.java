package com.magic.music.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author laoma
 * @create 2021-02-20 17:47
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时间的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
      log.info("start insert fill ...");
      this.setFieldValByName("createTime",new Date(),metaObject);
      this.setFieldValByName("updateTime",new Date(),metaObject);
    }

    // 更新时间的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ...");
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
