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
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/22
 * @since 1.0.0
 */
@Data
@TableName("bu_banner")
public class Banner extends SuperEntity<Banner> {

    /**
     * 描述
     */
    private String title;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 跳转
     */
    private String router;

    /**
     * 排序
     */
    private Integer sort;
}