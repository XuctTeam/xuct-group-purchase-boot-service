/**
 * Copyright (C), 2015-2020, xuct.net
 * FileName: ColumnEnum
 * Author:   xutao
 * Date:     2020/2/3 16:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.base.enums;

import cn.com.xuct.group.purchase.base.vo.Column;
import cn.com.xuct.group.purchase.constants.RConstants;
import cn.com.xuct.group.purchase.exception.SvrException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.checkerframework.checker.units.qual.C;

import java.lang.reflect.Array;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author user
 * @create 2020/2/3
 * @since 1.0.0
 */
public enum ColumnEnum {

    eq {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.eq(column.getColumn(), column.getValue());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.eq(column.getColumn(), column.getValue());
        }
    },

    nq {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.ne(column.getColumn(), column.getValue());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.ne(column.getColumn(), column.getValue());
        }
    },

    like {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.like(column.getColumn(), column.getValue());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.like(column.getColumn(), column.getValue());
        }
    },

    like_left {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.likeLeft(column.getColumn(), column.getValue());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.likeLeft(column.getColumn(), column.getValue());
        }
    },

    like_right {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.likeRight(column.getColumn(), column.getValue());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.likeRight(column.getColumn(), column.getValue());
        }
    },

    in {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.in(column.getColumn(), column.getValue());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.in(column.getColumn(), column.getValue());
        }
    },

    not_in {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.notIn(column.getColumn(), column.getValue());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.notIn(column.getColumn(), column.getValue());
        }
    },

    lt {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.lt(column.getColumn(), column.getValue());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.lt(column.getColumn(), column.getValue());
        }
    },

    le {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.le(column.getColumn(), column.getValue());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.le(column.getColumn(), column.getValue());
        }
    },

    gt {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.gt(column.getColumn(), column.getValue());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.gt(column.getColumn(), column.getValue());
        }
    },

    ge {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.ge(column.getColumn(), column.getValue());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.ge(column.getColumn(), column.getValue());
        }
    },

    is_not_null {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.isNull(column.getColumn());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.isNull(column.getColumn());
        }
    },

    is_null {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            qr.isNull(column.getColumn());
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            wr.isNull(column.getColumn());
        }
    },

    between {
        @Override
        public <T> void buildQuery(QueryWrapper<T> qr, Column column) {
            if (column.getValue() == null || !column.getValue().getClass().isArray()) {
                throw new SvrException("构建参数异常", Integer.parseInt(RConstants.ERROR));
            }
            int length = Array.getLength(column.getValue());
            Object[] os = new Object[length];
            for (int i = 0; i < os.length; i++) {
                os[i] = Array.get(column.getValue(), i);
            }
            if (os.length == 1) {
                qr.le(column.getColumn(), os[0]);
                return;
            }
            qr.between(column.getColumn(), os[0], os[1]);
        }

        @Override
        public <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column) {
            if (column.getValue() == null || !column.getValue().getClass().isArray()) {
                throw new SvrException("构建参数异常", Integer.parseInt(RConstants.ERROR));
            }
            int length = Array.getLength(column.getValue());
            Object[] os = new Object[length];
            for (int i = 0; i < os.length; i++) {
                os[i] = Array.get(column.getValue(), i);
            }
            if (os.length == 1) {
                wr.le(column.getColumn(), os[0]);
                return;
            }
            wr.between(column.getColumn(), os[0], os[1]);
        }
    };

    public abstract <T> void buildQuery(QueryWrapper<T> qr, Column column);


    public abstract <T> void buildUpdateQuery(UpdateWrapper<T> wr, Column column);
}