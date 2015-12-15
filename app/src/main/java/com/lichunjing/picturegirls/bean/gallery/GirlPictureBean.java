package com.lichunjing.picturegirls.bean.gallery;

import java.util.List;

/**
 * Created by lcj621400 on 2015/12/15.
 * 包含此分类下此图集的所有图片信息
 */
public class GirlPictureBean {
    /*
}
    {
        "count": 41,
            "fcount": 0,
            "galleryclass": 1,
            "id": 516,
            "img": "/ext/151214/284a7ff2d7021b769b78908bcb0fee28.jpg",
            "list": [
        {
            "gallery": 516,
                "id": 8628,
                "src": "/ext/151214/284a7ff2d7021b769b78908bcb0fee28.jpg"
        },
        ......
               ]
      }
     */

    private int count;//访问数
    private int focunt;//回复数
    private int galleryclass;//分类id
    private int id;//此分类下的此图集的id
    private String img;//封面图片
    private List<GirlGalleryBean> list;//此图集的所有图片

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFocunt() {
        return focunt;
    }

    public void setFocunt(int focunt) {
        this.focunt = focunt;
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

    public List<GirlGalleryBean> getList() {
        return list;
    }

    public void setList(List<GirlGalleryBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GirlPictureBean{" +
                "count=" + count +
                ", focunt=" + focunt +
                ", galleryclass=" + galleryclass +
                ", id=" + id +
                ", img='" + img + '\'' +
                ", list=" + list +
                '}';
    }
}
