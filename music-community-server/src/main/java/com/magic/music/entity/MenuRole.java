package com.magic.music.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MenuRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 菜单id
     */
    private Integer mid;

    /**
     * 角色id
     */
    private Integer rid;


}
