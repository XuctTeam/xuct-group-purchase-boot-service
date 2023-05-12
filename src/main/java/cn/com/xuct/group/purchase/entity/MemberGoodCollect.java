/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserGoodCollect
 * Author:   Derek Xu
 * Date:     2023/3/28 17:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/28
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bu_good_member_collect")
public class MemberGoodCollect extends SuperEntity<MemberGoodCollect> {

    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 商品ID
     */
    private Long goodId;
}