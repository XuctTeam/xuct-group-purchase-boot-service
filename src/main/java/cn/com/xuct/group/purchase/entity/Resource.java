/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: Resource
 * Author:   Derek Xu
 * Date:     2023/5/9 9:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.entity;

import cn.com.xuct.group.purchase.base.dao.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/9
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_resource")
@Data
public class Resource extends SuperEntity<Resource> {

    @NotNull
    private Long parentId;

    /**
     * 级别
     */
    private int level;

    /**
     * 类型  1 目录 2 菜单 3 按钮 4 外链
     */
    @NotNull
    private String category;

    /**
     * 标题
     */
    @NotNull
    private String title;

    /**
     * 组件
     */
    private String component;

    /**
     * 路径
     */
    @NotNull
    private String path;


    /**
     * 路径名称
     */
    private String pathName;

    /**
     * 权限标识
     */
    private String perm;

    /**
     * 重定向
     */
    private String redirect;

    /**
     * 连接地址
     */
    private String link;


    /**
     * 图标
     */
    @NotNull
    private String icon;

    /**
     * 是否隐藏
     */
    private boolean hide;

    /**
     * 是否全屏
     */
    private boolean full;


    private boolean affix;

    /**
     * 是否缓存
     */
    private boolean keepAlive;

    /**
     * 排序
     */
    private Integer sort;
}