package cn.xts.demo.service.impl;

import cn.xts.demo.entity.Members;
import cn.xts.demo.entity.vo.R;
import cn.xts.demo.mapper.MemberMapper;
import cn.xts.demo.service.MembersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/9/23 下午11:09
 */
@Service
public class MembersServiceImpl implements MembersService {

    @Autowired
    MemberMapper mapper;


    @Override
    public R<Members> updatePoint(Integer point, Integer id) {
        Members members = mapper.selectById(id);
        int lock_point = members.getLock_point();                //锁定积分
        int consume_point = members.getConsume_point();             //目前的消费积分
        int out_point = members.getOut_point();                     //目前的提现积分
        int limit_point = members.getLimit_point();              //每日分配积分上限
        lock_point = lock_point - point;
        consume_point = consume_point + point/2;
        out_point = out_point + point/2;


        //如果没达到每日分配积分上限
        if (limit_point - point >= 0) {
            members.setLimit_point(limit_point - point);
            members.setLock_point(lock_point);
            members.setConsume_point(consume_point);
            members.setOut_point(out_point);

            mapper.updateById(members);
            members = mapper.selectById(id);
            return R.ok("积分领取成功").putData(members);
        }

        return R.error("积分领取失败");
    }

    @Override
    public R distribution(Integer id) {

        Members members1 = mapper.selectById(id);           //本人

        if (members1.getReferer_3() != null) {
            int pid1 = members1.getReferer_3();                 //上级推荐人
            Members members2 = mapper.selectById(pid1);
            int lockPoint = members2.getLock_point();
            lockPoint = lockPoint+ 500;
            members2.setLock_point(lockPoint);
            mapper.updateById(members2);

            if (members2.getReferer_3() != null){
                int pid2 = members2.getReferer_3();                 //首级推荐人
                Members members3 = mapper.selectById(pid2);
                if (members3 != null) {
                    return R.ok("查无此人,上级添加成功");
                } else {
                    int lockPoint2 = members3.getLock_point();
                    lockPoint2 =lockPoint2 + 200;
                    members3.setLock_point(lockPoint2);
                    mapper.updateById(members3);
                    return R.ok("成功");
                }
            }else {
                return R.ok("二级查无此人");
            }
        }else {
            return R.ok("一级查无此人");
        }

    }

    @Override
    public R<Members> findById(Integer id) {
        return R.ok("查询成功").putData(mapper.selectById(id));
    }

    @Override
    public R<Members> forwarding(String mobile, Integer point,String type,Integer id) {
        QueryWrapper<Members> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Members::getMobile,mobile);
        Members members = mapper.selectOne(queryWrapper);                   //积分接收者

        Members members2 = mapper.selectById(id);                   //积分发送者

        if (type.equals("消费积分")){
            int consume_point =  members.getConsume_point();
            int consume_point2 = members2.getConsume_point();
            consume_point2 = consume_point2 - point;
            consume_point = consume_point + point;
            members.setConsume_point(consume_point);
            members2.setConsume_point(consume_point2);
            mapper.updateById(members);
            mapper.updateById(members2);
            return R.ok("积分转赠成功");
        }else if (type.equals("提现积分")){
            int out_point =  members.getOut_point();
            int out_point2 = members2.getOut_point();
            out_point2 = out_point2 - point;
            out_point = out_point + point;
            members.setOut_point(out_point);
            members2.setOut_point(out_point2);
            mapper.updateById(members);
            mapper.updateById(members2);
            return R.ok("积分转赠成功");
        }


        return R.ok("积分转赠失败");
    }

    @Override
    public R<List<Members>> findMyTeam(Integer id) {
        List<Members> membersList = new ArrayList<>(); //总
        List<Members> membersList1 = new ArrayList<>();  //一级
        List<Members> membersList2 = new ArrayList<>();  //二级
        Members members1 = mapper.selectById(id);           //本人
        Members members2 = mapper.selectById(members1.getReferer_3());
        membersList.add(members1);                          //列表中添加本人
        membersList.add(members2);                          //列表中添加本人


        //查询二级的人
        QueryWrapper<Members> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(Members::getReferer_3,id).isNotNull(Members::getReferer_3);
        membersList1 = mapper.selectList(queryWrapper1);


        for (Members members : membersList1) {
            QueryWrapper<Members> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(Members::getReferer_3,members.getId()).isNotNull(Members::getReferer_3);
            membersList2.addAll(mapper.selectList(queryWrapper2));
        }

        membersList.addAll(membersList1);
        membersList.addAll(membersList2);


        return R.ok("查询成功").putData(membersList);
    }

    @Override
    public void resetIntegral() {
        UpdateWrapper<Members> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().isNotNull(Members::getId).set(Members::getLimit_point, 500);
        mapper.update(null, updateWrapper);
    }
}
