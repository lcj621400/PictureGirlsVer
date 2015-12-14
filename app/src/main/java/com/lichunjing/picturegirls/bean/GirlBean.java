package com.lichunjing.picturegirls.bean;

/**
 * Created by lcj621400 on 2015/12/12.
 */
public class GirlBean {

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

    private int count;
    private int fcount;
    private int galleryclass;
    private int id;
    private String img;
    private int rcount;
    private int size;
    private long time;
    private String title;

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
        return "GirlBean{" +
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
