/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: GoodMapper
 * Author:   Derek Xu
 * Date:     2023/3/27 11:24
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapper;

import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.vo.result.GoodResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/27
 * @since 1.0.0
 */
public interface GoodMapper extends BaseMapper<Good> {


    /**
     * 获取商品详情
     *
     * @param gid
     * @param userId
     * @return
     */
    GoodResult getGoodInfo(@Param("gid") Long gid, @Param("userId") Long userId);
}