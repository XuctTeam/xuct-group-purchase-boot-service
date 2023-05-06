/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: GoodResult
 * Author:   Derek Xu
 * Date:     2023/3/28 18:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.result;

import cn.com.xuct.group.purchase.entity.Good;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/28
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodResult extends Good {

    @Schema(description = "是否收藏")
    private boolean collect;

    @Schema(description = "浏览量")
    private Integer browse;

    @Schema(description = "成交量")
    private Integer sell;

}