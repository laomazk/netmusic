package com.magic.music.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author laoma
 * @create 2021-03-09 22:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PassWd implements Serializable {
    private Integer id;
    private String oldPassword;
    private String newPassword;
}
