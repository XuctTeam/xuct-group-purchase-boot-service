/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserSumResult
 * Author:   Derek Xu
 * Date:     2023/4/26 12:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/26
 * @since 1.0.0
 */
@Data
public class MemberSumResult implements Serializable {

    @Schema(description = "积分统计")
    private Integer integralCount;

    @Schema(description = "浏览统计")
    private Integer browseCount;

    @Schema(description = "收藏统计")
    private Integer collectCount;

    @Schema(description = "优惠券统计")
    private Integer couponCount;
}