/**
 * Copyright (C), 2015-2023, 263企业通信
 * FileName: AdminMenuResult
 * Author:   Derek Xu
 * Date:     2023/5/9 9:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.vo.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/5/9
 * @since 1.0.0
 */
@Data
public class AdminMenuResult {

    @Schema(description = "路由访问路径")
    private String path;

    @Schema(description = "路由 name (对应页面组件 name, 可用作 KeepAlive 缓存标识 && 按钮权限筛选)")
    private String name;

    private String redirect;

    @Schema(description = "视图文件路径")
    private String component;

    @Schema(description = "路由元信息")
    private Meta meta;

    @Schema(description = "子路由")
    private List<AdminMenuResult> children;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {

        @Schema(description = "菜单和面包屑对应的图标")
        private String icon;

        @Schema(description = "路由标题 (用作 document.title || 菜单的名称)")
        private String title;

        @Schema(description = "路由外链时填写的访问地址")
        @JsonProperty("isLink")
        private String link;

        @Schema(description = "是否在菜单中隐藏 (通常列表详情页需要隐藏)")
        @JsonProperty("isHide")
        private boolean hide;

        @Schema(description = " 菜单是否全屏 (示例：数据大屏页面)")
        @JsonProperty("isFull")
        private boolean full;

        @Schema(description = "菜单是否固定在标签页中")
        @JsonProperty("isAffix")
        private boolean affix;

        @Schema(description = "当前路由是否缓存")
        @JsonProperty("isKeepAlive")
        private boolean keepAlive;
    }
}