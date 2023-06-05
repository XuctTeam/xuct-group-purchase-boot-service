/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: RedisCacheConstants
 * Author:   Derek Xu
 * Date:     2023/3/22 16:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.constants;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/22
 * @since 1.0.0
 */
public interface RedisCacheConstants {

    String MEMBER_CACHE_ABLE_CACHE_NAME = "group::purchase::member::cache#3600";


    String USER_CACHE_ROLE_NAME = "group::purchase::role::cache#3600";


    String USER_CACHE_USER_INFO = "group::purchase::user::cache#300";

    /**
     * 商品
     */
    String WARES_INVENTORY_REDIS_KEY = "group::purchase::wares::inventory:";


    String APP_CACHE_CONFIG_NAME = "group::purchase::app::config";

    /**
     * 登录验证码
     */
    String ADMIN_LOGIN_CAPTCHA_KEY = "group::purchase::login::captcha:";
}