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
package cn.com.xuct.group.purchase.controller.app;

import cn.com.xuct.group.purchase.base.res.R;
import cn.com.xuct.group.purchase.entity.MemberAddress;
import cn.com.xuct.group.purchase.service.MemberAddressService;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
public class MemberAddressController {

    private final MemberAddressService memberAddressService;

    @GetMapping("/list")
    @Operation(summary = "【用户】获取用户地址", description = "获取用户地址")
    @Parameters(value = {
            @Parameter(name = "searchValue", description = "名称或者手机号码")
    })
    public R<List<MemberAddress>> list(@RequestParam(value = "searchValue", required = false) String searchValue) {
        return R.data(memberAddressService.findList(StpUtil.getLoginIdAsLong(), searchValue));
    }

    @PostMapping
    @Operation(summary = "【用户】保存地址", description = "保存地址")
    public R<String> save(@RequestBody MemberAddress address) {
        if (address.getId() == null) {
            address.setMemberId(StpUtil.getLoginIdAsLong());
        }
        memberAddressService.saveAddress(address);
        return R.status(true);
    }

    @GetMapping()
    @Operation(summary = "【用户】查询地址", description = "查询地址")
    @Parameters(value = {
            @Parameter(name = "id", description = "地址ID")
    })
    public R<MemberAddress> get(@RequestParam("id") String id) {
        return R.data(memberAddressService.getById(Long.valueOf(id)));
    }

    @DeleteMapping
    @Operation(summary = "【用户】删除地址", description = "删除地址")
    @Parameters(value = {
            @Parameter(name = "id", description = "地址ID"),
    })
    public R<String> delete(@RequestParam("id") String id) {
        memberAddressService.delete(StpUtil.getLoginIdAsLong(), Long.valueOf(id));
        return R.status(true);
    }

    @GetMapping("/default")
    @Operation(summary = "【用户】获取默认地址", description = "获取默认地址")
    public R<MemberAddress> getDefault() {
        return R.data(memberAddressService.getDefault(StpUtil.getLoginIdAsLong()));
    }
}