/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: ILoginResultConvert
 * Author:   Derek Xu
 * Date:     2023/5/19 9:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.mapstruct;

import cn.com.xuct.group.purchase.entity.Member;
import cn.com.xuct.group.purchase.entity.User;
import cn.com.xuct.group.purchase.vo.result.LoginResult;
import cn.dev33.satoken.stp.SaTokenInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/19
 * @since 1.0.0
 */
@Mapper
public interface ILoginResultConvert {


    ILoginResultConvert INSTANCE = Mappers.getMapper(ILoginResultConvert.class);


    @Mappings({
            @Mapping(source = "member.id", target = "member.id"),
            @Mapping(source = "member.openId", target = "member.openId"),
            @Mapping(source = "member.avatar", target = "member.avatar"),
            @Mapping(source = "member.status", target = "member.status"),
            @Mapping(source = "member.phone", target = "member.phone"),
            @Mapping(source = "member.integral", target = "member.integral"),
            @Mapping(source = "member.nickname", target = "member.nickname"),
            @Mapping(source = "member.roleCode", target = "member.roleCode"),
            @Mapping(source = "member.createTime", target = "member.createTime"),
            @Mapping(source = "saTokenInfo.tokenName", target = "tokenName"),
            @Mapping(source = "saTokenInfo.tokenValue", target = "tokenValue"),
    })
    LoginResult memberToken2LoginResult(Member member, SaTokenInfo saTokenInfo);



    @Mappings({
            @Mapping(source = "user.id", target = "user.id"),
            @Mapping(source = "user.username", target = "user.username"),
            @Mapping(source = "user.avatar", target = "user.avatar"),
            @Mapping(source = "user.status", target = "user.status"),
            @Mapping(source = "user.roleId", target = "user.roleId"),
            @Mapping(source = "user.phone", target = "user.phone"),
            @Mapping(source = "user.nickname", target = "user.nickname"),
            @Mapping(source = "user.roleCode", target = "user.roleCode"),
            @Mapping(source = "user.createTime", target = "user.createTime"),
            @Mapping(source = "user.roleName", target = "user.roleName"),
            @Mapping(source = "saTokenInfo.tokenName", target = "tokenName"),
            @Mapping(source = "saTokenInfo.tokenValue", target = "tokenValue"),
    })
    LoginResult userToken2LoginResult(User user , SaTokenInfo saTokenInfo);
}