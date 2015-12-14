package com.lichunjing.picturegirls.bean;

import java.util.List;

/**
 * Created by lcj621400 on 2015/12/12.
 */
public class GirlArrayBean {
    /*
    "total": 160,
    "tngou": [
                {}
                {}
                {}
            ]
    */

    private int total;
    private List<GirlBean> tngou;

    public List<GirlBean> getTngou() {
        return tngou;
    }

    public void setTngou(List<GirlBean> tngou) {
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
        return "GirlArrayBean{" +
                "tngou=" + tngou +
                ", total=" + total +
                '}';
    }
}
