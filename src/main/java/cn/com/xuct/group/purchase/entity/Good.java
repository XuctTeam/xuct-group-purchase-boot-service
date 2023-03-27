/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: Good
 * Author:   Derek Xu
 * Date:     2023/3/27 11:11
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

import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/27
 * @since 1.0.0
 */
@Data
@TableName("bu_good")
public class Good extends SuperEntity<Good> {


    @Schema(title = "名称")
    private String name;

    @Schema(title = "首页图片")
    private String firstDrawing;

    @Schema(title = "详情轮播图" , description = "通过，分割")
    private String swiperImages;

    @Schema(title = "状态" , description = "0未上架 1已上架")
    private Integer status;

    @Schema(title = "开始售卖时间")
    private Date startTime;

    @Schema(title = "结束售卖时间")
    private Date endTime;

    @Schema(title = "库存")
    private Integer inventory;

    @Schema(title = "描述")
    private String detail;
}