/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserOpinion
 * Author:   Derek Xu
 * Date:     2023/4/28 18:25
 * Description: 意见
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 〈一句话功能简述〉<br>
 * 〈意见〉
 *
 * @author Derek Xu
 * @create 2023/4/28
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_member_opinion")
public class MemberOpinion extends SuperEntity<MemberOpinion> {

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "类型")
    @NotNull
    private String type;

    @Schema(description = "上传图片,逗号分割")
    private String images;

    @NotNull
    @Schema(description = "描述")
    private String remarks;

    @Schema(description = "反馈状态 0未反馈 1已反馈")
    private boolean status;

    @Schema(description = "反馈时间")
    private String feedbackTime;

    @Schema(description = "反馈内容")
    private String feedback;

    @Schema(description = "会员昵称")
    @TableField(exist = false)
    private String memberName;

    
    @Schema(description = "会员头像")
    @TableField(exist = false)
    private String memberAvatar;

}