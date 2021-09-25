package cn.xts.demo.service.impl;

import cn.xts.demo.entity.Members;
import cn.xts.demo.entity.vo.R;
import cn.xts.demo.mapper.MemberMapper;
import cn.xts.demo.service.MembersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        System.out.println(members1.toString());

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
}
