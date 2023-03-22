/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: LoginResult
 * Author:   Derek Xu
 * Date:     2023/3/18 14:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.result;

import cn.com.xuct.group.purchase.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/18
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResult implements Serializable {


    private String tokenValue;

    private String tokenName;

    private User user;
}