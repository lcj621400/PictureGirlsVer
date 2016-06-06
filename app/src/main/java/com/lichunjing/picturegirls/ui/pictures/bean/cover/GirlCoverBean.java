package com.lichunjing.picturegirls.ui.pictures.bean.cover;

/**
 * Created by lcj621400 on 2015/12/12.
 * 分类封面详细信息
 */
public class GirlCoverBean {

    /*
            "count": 155,
            "fcount": 0,
            "galleryclass": 1,
            "id": 466,
            "img": "/ext/151126/37e690b145bb70aea5e5b99d24cc8087.jpg",
            "rcount": 0,
            "size": 33,
            "time": 1448499098000,
            "title": "魅研社美女瑞莎Trista巨乳诱惑写真"
    */

    private int count;//访问数
    private int fcount;//收藏数
    private int galleryclass;//图片分类
    private int id;//图库ID编码
    private String img;//图库封面
    private int rcount;//回复数
    private int size;//图片多少张
    private long time;//发布时间
    private String title;//标题

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getGalleryclass() {
        return galleryclass;
    }

    public void setGalleryclass(int galleryclass) {
        this.galleryclass = galleryclass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "GirlCoverBean{" +
                "count=" + count +
                ", fcount=" + fcount +
                ", galleryclass=" + galleryclass +
                ", id=" + id +
                ", img='" + img + '\'' +
                ", rcount=" + rcount +
                ", size=" + size +
                ", time=" + time +
                ", title='" + title + '\'' +
                '}';
    }
}
