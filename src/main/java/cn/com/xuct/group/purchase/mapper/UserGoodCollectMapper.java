/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: UserGoodCollectMapper
 * Author:   Derek Xu
 * Date:     2023/3/28 17:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapper;

import cn.com.xuct.group.purchase.entity.Good;
import cn.com.xuct.group.purchase.entity.UserGoodCollect;
import cn.com.xuct.group.purchase.vo.result.UserGoodResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/28
 * @since 1.0.0
 */
public interface UserGoodCollectMapper extends BaseMapper<UserGoodCollect> {

    List<UserGoodResult> queryGoodByUserId(@Param("userId") Long userId);
}