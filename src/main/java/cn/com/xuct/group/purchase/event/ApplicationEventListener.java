/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: EventListener
 * Author:   Derek Xu
 * Date:     2023/6/6 17:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.event;

import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import cn.com.xuct.group.purchase.config.WxMaProperties;
import cn.com.xuct.group.purchase.constants.WxTemplateTypeEnum;
import cn.com.xuct.group.purchase.service.WxService;
import cn.com.xuct.group.purchase.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/6/6
 * @since 1.0.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationEventListener {

    private final WxService wxService;
    private final WxMaProperties wxMaProperties;

    @Async
    @EventListener
    public void orderEvent(OrderEvent orderEvent) {
        log.info("ApplicationEventListener:: orderEvent , {}", JsonUtils.obj2json(orderEvent));
        this.pushMemberSubscribeMessage(orderEvent.getOpenId(), orderEvent.getOrderId(), orderEvent.getStatus(),
                orderEvent.getWaresName(), orderEvent.getActionTime(), orderEvent.getRemarks());
    }


    /**
     * @param openId     用户openId
     * @param orderId    订单ID
     * @param status     订单状态
     * @param waresNames 商品名称
     * @param actionTime 操作时间
     * @param remarks    备注
     * @return:
     * @since: 1.0.0
     * @Author:Derek xu
     * @Date: 2023/6/6 16:57
     */
    private void pushMemberSubscribeMessage(final String openId, final String orderId, final String status,
                                            final String waresNames, final String actionTime, final String remarks) {

        Optional<WxMaProperties.Template> templateOpt = wxMaProperties.getTemplates().stream().filter(item -> item.getType().equals(WxTemplateTypeEnum.order)).findFirst();
        if (templateOpt.isEmpty()) {
            log.error("ApplicationEventListener:: pushMemberSubscribeMessage error , template not found");
            return;
        }
        try {
            wxService.pushSubscribeMsg(openId, templateOpt.get().getId(), templateOpt.get().getPage(), WxMaConstants.MiniProgramState.DEVELOPER, new HashMap<String, Object>() {{
                put("character_string1", orderId);
                put("phrase3", status);
                put("thing23", waresNames.length() > 20 ? waresNames.substring(0, 16) + "..." : waresNames);
                put("time12", actionTime);
                put("thing8", remarks);
            }});
        } catch (WxErrorException e) {
            log.error("ApplicationEventListener:: pushMemberSubscribeMessage error , {}", e.getMessage());
        }
    }
}