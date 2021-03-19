package com.magic.music.controller;

import com.magic.music.entity.Listener;
import com.magic.music.entity.vo.PassWd;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.service.ListenerService;
import com.magic.music.utils.FastDFSUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author laoma
 * @create 2021-03-19 9:51
 */
@RestController
public class UserInfoController {

    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @Value("${listener.singer.default.pic}")
    String defaultPic;

    @Autowired
    private ListenerService listenerService;

    @GetMapping("/my/info")
    public Listener getCurrentListener(Authentication authentication) {
        return ((Listener) authentication.getPrincipal());
    }

    @PutMapping("/my/info")
    public RespBean updateListener(@RequestBody Listener listener, Authentication authentication) {
        if (listenerService.updateById(listener)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(listener, authentication.getCredentials(), authentication.getAuthorities()));

            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @PutMapping("/my/pass")
    public RespBean updateUserPass(@RequestBody Map<String,Object> info){
        String oldPass = ((String) info.get("oldPass"));
        String pass = ((String) info.get("pass"));
        Integer lisId = ((Integer) info.get("lisId"));
        PassWd passWd = new PassWd();
        passWd.setId(lisId).setNewPassword(pass).setOldPassword(oldPass);
        return listenerService.updatePassword(passWd);
    }

    /**
     * 更新前端用户图片
     */
    @PostMapping("/my/updatepic")
    public RespBean updateListenerrPic(MultipartFile file, Integer id,Authentication authentication) {
        if (file.getOriginalFilename().endsWith(".jpg") || file.getOriginalFilename().endsWith(".png")) {
            String fileId = FastDFSUtils.upload(file);
            String url = nginxHost + fileId;

            // 拿到旧的url取出fileId,删掉远程的file
            Listener listener = listenerService.getById(id);
            String oldUrl = listener.getAvator();
            if (oldUrl != null && oldUrl.length() > 0 && !StringUtils.equals(defaultPic, oldUrl)) {
                String oldFileId = oldUrl.substring(nginxHost.length(), url.length());
                FastDFSUtils.delete(oldFileId);
            }
            listener.setAvator(url);
            if (listenerService.updateById(listener)) {
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(listener,authentication.getCredentials(),authentication.getAuthorities()));
                return RespBean.ok("更新成功", url);
            }
            return RespBean.error("更新失败");
        }
        return RespBean.error("更新失败", "图片格式应为[jpg][png]");
    }
}
