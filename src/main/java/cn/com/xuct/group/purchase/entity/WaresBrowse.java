/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: WaresBrowse
 * Author:   Derek Xu
 * Date:     2023/3/29 10:58
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
import lombok.EqualsAndHashCode;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品浏览〉
 *
 * @author Derek Xu
 * @create 2023/3/29
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bu_wares_browse")
public class WaresBrowse extends SuperEntity<WaresBrowse> {

    @Schema(title = "商品ID")
    private Long waresId;

    @Schema(title = "浏览量")
    private Integer num;
}