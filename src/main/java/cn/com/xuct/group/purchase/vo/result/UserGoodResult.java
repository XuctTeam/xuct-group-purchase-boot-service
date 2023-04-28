/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserGoodResult
 * Author:   Derek Xu
 * Date:     2023/4/28 13:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.result;

import cn.com.xuct.group.purchase.entity.Good;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/28
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserGoodResult extends Good implements Serializable {

    private Date createTime;
}