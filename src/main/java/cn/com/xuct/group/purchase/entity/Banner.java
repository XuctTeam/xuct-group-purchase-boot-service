/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: Banner
 * Author:   Derek Xu
 * Date:     2023/3/22 10:44
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

import javax.validation.constraints.NotNull;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/22
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bu_banner")
public class Banner extends SuperEntity<Banner> {

    /**
     * 描述
     */
    @NotNull
    private String title;

    /**
     * 图片地址
     */
    @NotNull
    private String image;


    @Schema(description = "状态 0禁用 1正常")
    private Integer status;

    /**
     * 跳转
     */
    @NotNull
    private String router;

    /**
     * 排序
     */
    @NotNull
    private Integer sort;
}