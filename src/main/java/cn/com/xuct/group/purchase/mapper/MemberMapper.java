/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserMapper
 * Author:   Derek Xu
 * Date:     2023/3/16 20:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapper;

import cn.com.xuct.group.purchase.entity.Member;
import cn.com.xuct.group.purchase.vo.result.MemberSumResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/16
 * @since 1.0.0
 */
public interface MemberMapper extends BaseMapper<Member> {

    MemberSumResult memberSum(@Param("memberId")Long memberId);
}