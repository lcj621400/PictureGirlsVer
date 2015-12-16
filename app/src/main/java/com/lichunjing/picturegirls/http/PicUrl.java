package com.lichunjing.picturegirls.http;

/**
 * Created by lcj621400 on 2015/12/12.
 */
public class PicUrl {

    /**
     * 下载图片时，需要添加到url的前面
     */
    public static final String BASE_IMAGE_URL="http://tnfs.tngou.net/image";

    /**
     * 获取封面列表信息
     */
    public static final String GET_IMAGE_URL="http://www.tngou.net/tnfs/api/list?id=%s&page=%s&rows=%s";

    /**
     * 获取此分类下，此封面图集的列表信息
     */
    public static final String GET_GALLERY_URL="http://www.tngou.net/tnfs/api/show?id=%s";
}
