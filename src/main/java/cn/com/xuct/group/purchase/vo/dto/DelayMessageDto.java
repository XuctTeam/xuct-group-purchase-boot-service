/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: DelayMessageDto
 * Author:   Derek Xu
 * Date:     2023/5/17 8:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.dto;

import cn.com.xuct.group.purchase.constants.EventCodeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/17
 * @since 1.0.0
 */
@Data
@Builder
public class DelayMessageDto<T> {

    private Date current;


    private EventCodeEnum code;


    private T data;
}