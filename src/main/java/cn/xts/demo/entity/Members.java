package cn.xts.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: KenChen
 * @Description: 会员的实体类
 * @Date: Create in  2021/9/23 下午10:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("of_shop_members")
public class Members {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("3级推荐人id，上级推荐人的id")
    private Integer referer_3;

    @ApiModelProperty("锁定福利金，就是注册就给的那一万")
    private Integer lock_point;

    @ApiModelProperty("可用福利金，从那一万里拿")
    private Integer usable_point;

    @ApiModelProperty("每日限额500积分")
    private Integer limit_point;

    @ApiModelProperty("消费积分")
    private Integer consume_point;

    @ApiModelProperty("提现积分")
    private Integer out_point;

}
