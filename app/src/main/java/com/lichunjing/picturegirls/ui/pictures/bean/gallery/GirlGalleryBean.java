package com.lichunjing.picturegirls.ui.pictures.bean.gallery;

/**
 * Created by lcj621400 on 2015/12/15.
 * 每个分类下，图集的详细信息
 */
public class GirlGalleryBean {

    /*{
        "gallery": 516,
            "id": 8628,
            "src": "/ext/151214/284a7ff2d7021b769b78908bcb0fee28.jpg"
    }*/

    private int gallery;
    private int id;
    private String src;//图片地址

    public int getGallery() {
        return gallery;
    }

    public void setGallery(int gallery) {
        this.gallery = gallery;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "GirlGalleryBean{" +
                "gallery=" + gallery +
                ", id=" + id +
                ", src='" + src + '\'' +
                '}';
    }
}
