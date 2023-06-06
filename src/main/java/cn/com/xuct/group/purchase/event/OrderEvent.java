/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: OrderEvent
 * Author:   Derek Xu
 * Date:     2023/6/6 17:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/6/6
 * @since 1.0.0
 */
@Getter
public class OrderEvent extends ApplicationEvent {

    private final String orderId;

    private final String openId;

    private final String status;

    private final String waresName;

    private final String actionTime;

    private final String remarks;

    public OrderEvent(Object source, String orderId, String openId, String status, String waresName, String actionTime, String remarks) {
        super(source);
        this.orderId = orderId;
        this.openId = openId;
        this.status = status;
        this.waresName = waresName;
        this.actionTime = actionTime;
        this.remarks = remarks;
    }
}