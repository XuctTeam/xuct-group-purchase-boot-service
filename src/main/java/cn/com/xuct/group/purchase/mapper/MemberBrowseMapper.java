/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: UserBrowseMapper
 * Author:   Derek Xu
 * Date:     2023/4/25 22:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapper;

import cn.com.xuct.group.purchase.entity.MemberBrowse;
import cn.com.xuct.group.purchase.entity.Wares;
import cn.com.xuct.group.purchase.vo.result.MemberBrowseWaresResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/4/25
 * @since 1.0.0
 */
public interface MemberBrowseMapper extends BaseMapper<MemberBrowse> {


    void addUserBrowse(@Param("memberId")final Long memberId , @Param("waresId")Long waresId);

    List<MemberBrowseWaresResult> list(@Param("memberId")final Long memberId);
}