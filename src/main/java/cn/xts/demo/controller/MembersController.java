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


    @ApiOperation(value = "查询个人信息")
    @GetMapping(value = "/members/findById")
    public R<Members> findById(@RequestParam("id") Integer id){
        return service.findById(id);
    }


    @ApiOperation(value = "积分转赠")
    @PostMapping(value = "/members/pointForwarding")
    public R<Members> pointForwarding(
            @RequestParam("id") Integer id,
            @RequestParam("point") Integer point,
            @RequestParam("mobile") String mobile,
            @RequestParam("type") String type){
        return service.forwarding(mobile,point,type,id);
    }


    @ApiOperation(value = "查询二级分销相关人员")
    @GetMapping(value = "/members/findMyTeam")
    public R<List<Members>> findMyTeam(@RequestParam("id") Integer id){
        return service.findMyTeam(id);
    }


}
