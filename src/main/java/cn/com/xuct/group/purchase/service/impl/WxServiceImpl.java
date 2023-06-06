/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: WxServiceImpl
 * Author:   Derek Xu
 * Date:     2023/6/6 10:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.com.xuct.group.purchase.config.WxMaConfiguration;
import cn.com.xuct.group.purchase.service.WxService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/6/6
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class WxServiceImpl implements WxService {

    private final WxMaConfiguration wxMaConfiguration;

    @Override
    public WxMaJscode2SessionResult jsCode2SessionInfo(String code) throws WxErrorException {
        return wxMaConfiguration.getMaService().jsCode2SessionInfo(code);
    }

    @Override
    public void pushSubscribeMsg(String openId, String templateId, String page, String miniprogramState, Map<String, Object> dataMap) throws WxErrorException {
        WxMaSubscribeMessage subscribeMessage = WxMaSubscribeMessage.builder().page(page)
                .miniprogramState(miniprogramState)
                .templateId(templateId).toUser(openId).build();
        for (String key : dataMap.keySet()) {
            subscribeMessage.addData(new WxMaSubscribeMessage.MsgData(key, dataMap.get(key).toString()));
        }
        wxMaConfiguration.getMaService().getMsgService().sendSubscribeMsg(subscribeMessage);
    }
}