/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: WxService
 * Author:   Derek Xu
 * Date:     2023/6/6 10:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/6/6
 * @since 1.0.0
 */
public interface WxService {

    /**
     * 通过code获取session信息
     *
     * @param code
     * @return
     * @throws WxErrorException
     */

    WxMaJscode2SessionResult jsCode2SessionInfo(String code) throws WxErrorException;

    /**
     * 发送订阅消息
     *
     * @param openId
     * @param templateId
     * @param miniprogramState
     * @param page
     * @param dataMap
     * @throws WxErrorException
     */
    void pushSubscribeMsg(String openId, String templateId, String page, String miniprogramState,  Map<String, Object> dataMap) throws WxErrorException;
}