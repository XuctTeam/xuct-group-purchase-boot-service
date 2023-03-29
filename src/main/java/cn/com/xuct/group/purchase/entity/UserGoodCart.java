/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserCart
 * Author:   Derek Xu
 * Date:     2023/3/29 9:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/29
 * @since 1.0.0
 */
@Data
@TableName("bu_good_user_cart")
public class UserGoodCart extends SuperEntity<UserGoodCart> {

    @Schema(title = "用户ID")
    private Long userId;

    @Schema(title = "商品ID")
    private Long goodId;

    @Schema(title = "商品数量")
    private Integer num;
}