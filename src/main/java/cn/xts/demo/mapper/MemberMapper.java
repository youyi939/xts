package cn.xts.demo.mapper;

import cn.xts.demo.entity.Members;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: KenChen
 * @Description: 会员mapper类
 * @Date: Create in  2021/9/23 下午10:59
 */
@Mapper
public interface MemberMapper extends BaseMapper<Members> {

}
