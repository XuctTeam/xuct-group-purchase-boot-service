/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: DeleteCartGoodParam
 * Author:   Derek Xu
 * Date:     2023/4/3 9:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/3
 * @since 1.0.0
 */
@Data
public class CartManyGoodParam implements Serializable {


    @Schema(description = "购物车商品IDS")
    private List<Long> gids;
}