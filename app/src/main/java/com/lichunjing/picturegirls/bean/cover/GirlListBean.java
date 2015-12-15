package com.lichunjing.picturegirls.bean.cover;

import java.util.List;

/**
 * Created by lcj621400 on 2015/12/12.
 * 图片分类列表
 */
public class GirlListBean {
    /*
    "total": 160,
    "tngou": [
                {}
                {}
                {}
            ]
    */

    private int total;
    private List<GirlCoverBean> tngou;

    public List<GirlCoverBean> getTngou() {
        return tngou;
    }

    public void setTngou(List<GirlCoverBean> tngou) {
        this.tngou = tngou;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "GirlListBean{" +
                "tngou=" + tngou +
                ", total=" + total +
                '}';
    }
}
