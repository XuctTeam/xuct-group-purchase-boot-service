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
import cn.com.xuct.group.purchase.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class DelayMessage<T> {

    private Date current;


    private EventCodeEnum code;


    private T data;


    public static <T> String ofMessage(EventCodeEnum code, T data) {
        return JsonUtils.obj2json(DelayMessage.builder().current(new Date()).code(code).data(data).build());
    }
}