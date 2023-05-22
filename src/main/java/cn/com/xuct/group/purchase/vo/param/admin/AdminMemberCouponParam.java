/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminMemberCouponParam
 * Author:   Derek Xu
 * Date:     2023/5/22 14:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/22
 * @since 1.0.0
 */
@Data
public class AdminMemberCouponParam {

    @NotNull
    private Long memberId;

    @NotNull
    private Long couponId;

    @NotEmpty
    private List<String> times;
}