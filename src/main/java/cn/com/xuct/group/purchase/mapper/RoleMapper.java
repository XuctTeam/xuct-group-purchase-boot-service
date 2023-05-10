/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: RoleMapper
 * Author:   Derek Xu
 * Date:     2023/3/16 21:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapper;

import cn.com.xuct.group.purchase.entity.Resource;
import cn.com.xuct.group.purchase.entity.Role;
import cn.com.xuct.group.purchase.vo.dto.ResourceButtonDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/16
 * @since 1.0.0
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 用户菜单
     *
     * @param id
     * @return
     */
    List<Resource> getUserMenuList(@Param("roleId") Long roleId);

    /**
     * 用户按钮权限
     *
     * @param id
     * @return
     */
    List<ResourceButtonDto> getUserButtonList(@Param("roleId") Long roleId);
}