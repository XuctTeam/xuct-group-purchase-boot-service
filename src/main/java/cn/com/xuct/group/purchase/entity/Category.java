/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: Category
 * Author:   Derek Xu
 * Date:     2023/5/17 9:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/17
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bu_category")
public class Category extends SuperEntity<Category> {

    @Schema(description = "名称")
    private String name;


    @Schema(description = "排序")
    private Integer sort;
}