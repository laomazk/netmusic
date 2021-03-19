package com.magic.music.controller.client;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.Comment;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/user/comment/")
public class UserCommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     */
    @PostMapping("/")
    public RespBean addComment(@RequestBody Comment comment){
        if(commentService.save(comment)){
            return RespBean.ok("评论成功");
        }
        return RespBean.error("评论失败");
    }

    /**
     * 修改评论
     */
    @PutMapping("/")
    public RespBean updateComment(@RequestBody Comment comment){
        if(commentService.updateById(comment)){
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/delete/{id}")
    public RespBean deleteComment(@PathVariable Integer id){
        if(commentService.removeById(id)){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * 根据主键查询整个对象
     */
    @GetMapping("/id/{id}")
    public Comment selectByPrimaryKey(@PathVariable Integer id){
        return commentService.getById(id);
    }

    /**
     * 查询所有评论
     */
    @GetMapping("/")
    public List<Comment> getAllComment(){
        return commentService.list();
    }

    /**
     *查询某个歌曲下的所有评论
     */
    @GetMapping("/song/{songId}")
    public List<Comment> getAllCommentOfSong(@PathVariable Integer songId){
        return commentService.list(new QueryWrapper<Comment>().eq("song_id",songId));
    }

    /**
     *查询某个歌单下的所有评论
     */
    @GetMapping("/songList/{songListId}")
    public List<Comment> getAllCommentOfSongList(@PathVariable Integer songListId){
        return commentService.list(new QueryWrapper<Comment>().eq("song_list_id",songListId));
    }

    /**
     * todo 考虑redis存储
     * 给某个评论点赞
     */
    @PutMapping("/like")
    public RespBean like(@RequestBody Comment comment){
        if(commentService.updateById(comment)){
            return RespBean.ok("点赞成功");
        }
        return RespBean.error("点赞失败");
    }
}

