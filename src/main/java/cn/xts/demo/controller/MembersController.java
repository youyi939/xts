package cn.xts.demo.controller;

import cn.xts.demo.entity.Members;
import cn.xts.demo.entity.vo.R;
import cn.xts.demo.service.MembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/9/23 下午11:10
 */
@Api(tags = "会员controller")
@RestController
@CrossOrigin
public class MembersController {

    @Autowired
    MembersService service;

    @ApiOperation(value = "做任务给积分")
    @GetMapping(value = "/members/updatePoint")
    public R updatePoint(@RequestParam("usablePoint") Integer point, @RequestParam("id") Integer id){
        return service.updatePoint(point,id);
    }


    @ApiOperation(value = "二级分销接口")
    @GetMapping(value = "/members/distribution")
    public R distribution(@RequestParam("id") Integer id){
        return service.distribution(id);
    }

}
