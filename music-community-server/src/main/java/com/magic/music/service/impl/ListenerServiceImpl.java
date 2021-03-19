package com.magic.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.Listener;
import com.magic.music.entity.ListenerRole;
import com.magic.music.entity.vo.PassWd;
import com.magic.music.entity.vo.RegListener;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.mapper.ListenerMapper;
import com.magic.music.service.ListenerRoleService;
import com.magic.music.service.ListenerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@Service
public class ListenerServiceImpl extends ServiceImpl<ListenerMapper, Listener> implements ListenerService, UserDetailsService {
    @Autowired
    ListenerMapper listenerMapper;

    @Autowired
    ListenerRoleService listenerRoleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${listener.singer.default.pic}")
    String defaultPic;


    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Listener> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Listener listener = listenerMapper.selectOne(wrapper);
        if (listener == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        listener.setRoles(listenerMapper.getListenerRolesById(listener.getId()));
        return listener;
    }


    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public boolean addListener(Listener listener) {
        String password = listener.getPassword();
        String encode = new BCryptPasswordEncoder().encode(password);
        listener.setPassword(encode);
        listener.setAvator(defaultPic);

        if (super.save(listener)) {
            Listener listenerWithId = super.getOne(new QueryWrapper<Listener>().eq("password", encode));
            ListenerRole listenerRole = new ListenerRole();
            listenerRole.setLid(listenerWithId.getId())
                    .setRid(Integer.valueOf(3));
            if (listenerRoleService.save(listenerRole)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public RespBean updatePassword(PassWd passWd) {
        Listener listener = super.getById(passWd.getId());
        String encodePd = listener.getPassword();
        boolean matches = passwordEncoder.matches(passWd.getOldPassword(), encodePd);
        if (!matches) {
            return RespBean.error("原密码输入错误！");
        }
        listener.setPassword(passwordEncoder.encode(passWd.getNewPassword()));
        if (super.updateById(listener)) {
            return RespBean.ok("修改密码成功");
        }
        return RespBean.error("修改密码失败");
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public RespBean regListener(RegListener regListener) {
        QueryWrapper<Listener> wrapper = new QueryWrapper<>();
        wrapper.eq("username", regListener.getUsername());
        Listener selectlistener = listenerMapper.selectOne(wrapper);
        if (selectlistener != null) {
            return RespBean.error("注册失败", "用户名已存在");
        }
        Listener listener = new Listener();
        BeanUtils.copyProperties(regListener, listener);
        listener.setPassword(passwordEncoder.encode(regListener.getPassword()));
        listener.setAvator(defaultPic);
        if (super.save(listener)) {
            return RespBean.ok("注册成功");
        }
        return RespBean.error("注册失败");
    }


}
