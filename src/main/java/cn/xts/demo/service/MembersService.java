package cn.xts.demo.service;

import cn.xts.demo.entity.Members;
import cn.xts.demo.entity.vo.R;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/9/23 下午11:09
 */
@Service
public interface MembersService {

    /**
     * 做任务返积分,从锁定的积分中扣除积分，加入到可用积分中。每天做任务最多给500积分
     * @param point 奖励的积分是多少
     * @return 会员对象，方便前端拿各种数据
     */
    R<Members> updatePoint(Integer point, Integer id);


    /**
     * 二级分销接口,我邀请一个人，这个人变身成为人民币战士了，我就会得到500积分，
     * 如果这个人，再次邀请了一个人，并且这个人也升级成了人民币战士，我就会得到200积分，我邀请的那个人会得到500。
     * @param id 用户id
     * @return
     */
    R distribution( Integer id);


    /**
     * 根据id查询用户信息
     * @param id 用户id
     * @return
     */
    R<Members> findById(Integer id);


    /**
     * 积分转赠
     * @param mobile 电话号
     * @param point 积分
     * @param type 积分类型：/提现积分和消费积分
     * @return
     */
    R<Members> forwarding(String mobile,Integer point,String type,Integer id);


    /**
     * android查询我的团队接口，查询出二级分销相关的团队人员名单，给前端返回list，安卓端渲染listView
     * @param id 登陆人id
     * @return 用户的list
     */
    R<List<Members>> findMyTeam(Integer id);


    /**
     * 重置每天做任务的积分上限
     */
    void resetIntegral();

}
