/**
 * Copyright (C), 2015-2020, xuct.net
 * FileName: Column
 * Author:   xutao
 * Date:     2020/2/3 16:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.base.vo;

import cn.com.xuct.group.purchase.base.enums.ColumnEnum;
import cn.com.xuct.group.purchase.constants.RConstants;
import cn.com.xuct.group.purchase.exception.SvrException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author user
 * @create 2020/2/3
 * @since 1.0.0
 */
@Data
public class Column {

    private String column;

    private Object value;

    private ColumnEnum columnEnum;

    private Column() {
    }

    public static Column of(String column, Object value) {
        Column c = new Column();
        c.setColumn(column);
        c.setValue(value);
        c.setColumnEnum(ColumnEnum.eq);
        return c;
    }

    public static Column of(String column, ColumnEnum columnEnum) {
        Column.checkedIn(columnEnum);
        Column c = new Column();
        c.setColumn(column);
        c.setColumnEnum(columnEnum);
        return c;
    }

    public static Column of(String column, Object value, ColumnEnum columnEnum) {
        Column.checkedIn(columnEnum);
        Column c = new Column();
        c.setColumn(column);
        c.setValue(value);
        c.setColumnEnum(columnEnum);
        return c;
    }

    public static Column of(String column, Object[] values, ColumnEnum columnEnum) {
        Column.checkedIn(columnEnum);
        Column c = new Column();
        c.setColumn(column);
        c.setValue(values);
        c.setColumnEnum(columnEnum);
        return c;
    }

    public static <T> Column in(String column, Collection<T> values) {
        Column c = new Column();
        c.setColumn(column);
        c.setValue(Sets.newHashSet(values));
        c.setColumnEnum(ColumnEnum.in);
        return c;
    }

    public <T> void buildQuery(QueryWrapper<T> qr) {
        this.getColumnEnum().buildQuery(qr, this);
    }


    public <T> void buildUpdateQuery(UpdateWrapper<T> wr) {
        this.getColumnEnum().buildUpdateQuery(wr, this);
    }


    private static void checkedIn(ColumnEnum columnEnum) {
        if (columnEnum.equals(ColumnEnum.in)) {
            throw new SvrException("请使用Column.in方法", Integer.parseInt(RConstants.ERROR));
        }
    }
}