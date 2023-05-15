/**
 * Copyright (C), 2015-2019, xuct.net
 * FileName: PageDatga
 * Author:   xutao
 * Date:     2019/12/27 16:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.base.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author xutao
 * @create 2019/12/27
 * @since 1.0.0
 */
@Data
public class PageData<T> {

    //时间戳
    private Long timestamp = System.currentTimeMillis();

    private Long pageTotal;

    //总条数
    private long total;

    private long pageSize;

    private long pageNum;

    //数据
    private List<T> list = Lists.newArrayList();

    /**
     * 功能描述: <br>
     * 〈〉
     *
     * @param page
     * @return:com.net263.vcs.api.server.common.vo.PageData
     * @since: 1.0.0
     * @Author:
     * @Date: 2019/12/27 16:40
     */
    public PageData<T> put(IPage<T> page) {
        this.setList(page.getRecords());
        this.setPageTotal(page.getPages());
        this.setTotal(page.getTotal());
        this.setPageNum(page.getCurrent());
        this.setPageSize(page.getSize());
        return this;
    }

    /**
     * @param list
     * @return
     */
    public PageData<T> put(List<T> list) {
        this.setList(list);
        this.setPageTotal(1L);
        this.setTotal(list.size());
        return this;
    }
}