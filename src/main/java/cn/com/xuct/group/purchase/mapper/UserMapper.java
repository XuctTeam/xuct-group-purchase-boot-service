/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserMapper
 * Author:   Derek Xu
 * Date:     2023/5/12 11:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapper;

import cn.com.xuct.group.purchase.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/12
 * @since 1.0.0
 */
public interface UserMapper extends BaseMapper<User> {


    void updateUserRoleToNull(@Param("roleId")Long roleId);
}