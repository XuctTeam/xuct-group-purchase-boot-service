/**
 * Copyright (C), 2015-2023, 楚恬商场
 * FileName: UserAddressController
 * Author:   Derek Xu
 * Date:     2023/3/23 17:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.controller;

import cn.com.xuct.group.purchase.base.enums.SortEnum;
import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.base.vo.Sort;
import cn.com.xuct.group.purchase.entity.UserAddress;
import cn.com.xuct.group.purchase.service.UserAddressService;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/23
 * @since 1.0.0
 */
@Tag(name = "【用户联系地址模块】")
@RequestMapping("/api/v1/address")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserAddressController {

    private final UserAddressService userAddressService;

    @Operation(summary = "获取用户地址", description = "获取用户地址")
    @GetMapping("/list")
    public R<List<UserAddress>> list() {
        return R.data(userAddressService.find(Lists.newArrayList(Column.of("user_id", StpUtil.getLoginIdAsLong())), Sort.of("create_time", SortEnum.desc)));
    }

    @Operation(summary = "保存地址", description = "保存地址")
    @PostMapping
    public R<String> save(@RequestBody UserAddress address){
        address.setUserId(StpUtil.getLoginIdAsLong());
        userAddressService.saveAddress(address);
        return R.status(true);
    }
}