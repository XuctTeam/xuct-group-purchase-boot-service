/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: Comment
 * Author:   Derek Xu
 * Date:     2023/4/22 18:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/22
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bu_member_comment")
public class MemberComment extends SuperEntity<MemberComment> {

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "来源用户")
    private String user;

    @Schema(description = "发送者头像")
    private String avatar;

    @Schema(description = "发送者昵称")
    private String nickName;

    @NotNull
    @Schema(description = "内容")
    private String content;
}