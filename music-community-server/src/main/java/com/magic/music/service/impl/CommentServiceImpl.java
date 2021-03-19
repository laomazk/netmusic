package com.magic.music.service.impl;

import com.magic.music.entity.Comment;
import com.magic.music.mapper.CommentMapper;
import com.magic.music.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
