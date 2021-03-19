package com.magic.music.controller.client;


import com.magic.music.entity.Rank;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评价 前端控制器
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/user/rank/")
public class UserRankController {

    @Autowired
    private RankService rankService;

    /**
     * 新增评价
     */
    @PostMapping("/")
    public RespBean addRank(@RequestBody Rank rank){
        if(rankService.save(rank)){
            return RespBean.ok("评价成功");
        }
        return RespBean.error("评价失败,你已经评价过了");
    }

    /**
     * 计算平均分
     */
    @GetMapping("/{id}")
    public Integer getSongListRankById(@PathVariable Integer id){
        return rankService.getSongListRankById(id);
    }

}

