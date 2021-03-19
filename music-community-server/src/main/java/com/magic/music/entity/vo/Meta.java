package com.magic.music.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author laoma
 * @create 2021-02-23 22:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Meta implements Serializable {
    private Boolean keepAlive;

    private Boolean requireAuth;
}
