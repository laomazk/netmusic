package com.magic.music.controller.admin;

import com.magic.music.entity.Listener;
import com.magic.music.entity.Menu;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author laoma
 * @create 2021-02-27 23:50
 */
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
    @Autowired
    MenuService menuService;
    @GetMapping("/menu")
    private List<Menu> getMenuByListenerId(){
        List<Menu> menuByListenerId = menuService.getMenuByListenerId(((Listener) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        return menuByListenerId;
    }
}
