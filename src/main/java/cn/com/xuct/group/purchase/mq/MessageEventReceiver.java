/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: MessageEventReceiver
 * Author:   Derek Xu
 * Date:     2023/5/17 8:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mq;

import cn.com.xuct.group.purchase.service.WaresService;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import cn.com.xuct.group.purchase.vo.dto.WaresDelayedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/17
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageEventReceiver {


    private final WaresService waresService;


    /**
     * 监听过期消息
     *
     * @param event
     */
    @EventListener
    public void receiveMessage(MessageEvent event) {
        switch (event.getCode()) {
            case wares_expire -> this.waresExpire(event.getData());
            default -> log.error("MessageEventReceiver:: message not support , code = {}", event.getCode());
        }
    }


    private <T> void waresExpire(T data) {
        WaresDelayedDto waresDelayedDto = JsonUtils.json2pojo(String.valueOf(data), WaresDelayedDto.class);
        if (waresDelayedDto == null) {
            return;
        }
        waresService.waresExpire(waresDelayedDto.getWaresId(), waresDelayedDto.getVersion());
    }
}