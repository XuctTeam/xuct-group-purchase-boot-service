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

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.entity.UserAddress;
import cn.com.xuct.group.purchase.service.UserAddressService;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/list")
    @Operation(summary = "【用户】获取用户地址", description = "获取用户地址")
    public R<List<UserAddress>> list(@RequestParam(value = "searchValue", required = false) String searchValue) {
        return R.data(userAddressService.findList(StpUtil.getLoginIdAsLong(), searchValue));
    }

    @PostMapping
    @Operation(summary = "【用户】保存地址", description = "保存地址")
    public R<String> save(@RequestBody UserAddress address) {
        if (address.getId() == null) {
            address.setUserId(StpUtil.getLoginIdAsLong());
        }
        userAddressService.saveAddress(address);
        return R.status(true);
    }

    @GetMapping()
    @Operation(summary = "【用户】查询地址", description = "查询地址")
    public R<UserAddress> get(@RequestParam("id") String id) {
        return R.data(userAddressService.getById(Long.valueOf(id)));
    }

    @DeleteMapping
    @Operation(summary = "【用户】删除地址", description = "删除地址")
    public R<String> delete(@RequestParam("id") String id) {
        userAddressService.delete(StpUtil.getLoginIdAsLong(), Long.valueOf(id));
        return R.status(true);
    }

    @GetMapping("/default")
    @Operation(summary = "【用户】获取默认地址", description = "获取默认地址")
    public R<UserAddress> getDefault() {
        return R.data(userAddressService.getDefault(StpUtil.getLoginIdAsLong()));
    }
}