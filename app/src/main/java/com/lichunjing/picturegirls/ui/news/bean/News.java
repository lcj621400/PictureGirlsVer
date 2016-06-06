package com.lichunjing.picturegirls.ui.news.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class News {

    /**
     * status : true
     * total : 2514
     * tngou : [{"count":23,"description":"据信息时报报道今年31岁，已经是两个孩子的母亲，但她十多年以来的生活一直没有离开过父亲的\u201c朋友圈\u201d，她是原深圳政法委书记蒋尊玉的独生女蒋丹丹，被控与父亲共同收受贿赂现金7万元以及一辆价值约84万元的保时捷跑车，5月31日上午在广州中院过堂","fcount":0,"fromname":"中国青年网","fromurl":"http://news.youth.cn/gn/201606/t20160601_8071924.htm","id":10407,"img":"/top/160601/c7049c44f933783352ddbec2355074a4.jpg","keywords":"蒋尊玉女儿受审","rcount":0,"time":1464748266000,"title":"蒋尊玉女儿受审:从小到大 一路有人\"示好照顾\"","topclass":1},{"count":21,"description":"文理跨学科教学受到同学们的喜爱，虽然实验教学成本比普通教学高，但可以明显提高文科生的科学思维能力和动手能力","fcount":0,"fromname":"中国青年网","fromurl":"http://news.youth.cn/sh/201605/t20160531_8069229_6.htm","id":10406,"img":"/top/default.jpg","keywords":"女友探班母猴吃醋","rcount":0,"time":1464741394000,"title":"博士男子曾被母猴示爱 女友探班母猴吃醋","topclass":1},{"count":20,"description":"美女纽约广场汉服快闪多名侨外华人美女身穿汉服来到纽约时代广场玩起了快闪，旁边百老汇剧场的两名演员带妆路过，即兴加入队伍斗舞，吸引路人驻足观看","fcount":0,"fromname":"中国青年网","fromurl":"http://news.youth.cn/sh/201606/t20160601_8069613_12.htm","id":10405,"img":"/top/default.jpg","keywords":"美女汉服应聘空姐","rcount":0,"time":1464741380000,"title":"美女汉服应聘空姐 大秀中国风古典范儿(图)","topclass":1},{"count":14,"description":"视频监控违规车辆违法记录近日，江西南昌交警通过智能监控发现一辆黑色越野车竟有777条违章，按每个违章扣6分罚100元算，该车将被扣4662分，罚77000多元","fcount":0,"fromname":"新浪新闻","fromurl":"http://news.sina.com.cn/s/wh/2016-05-31/doc-ifxsqxxs8098875.shtml","id":10404,"img":"/top/160601/85fa620dd178a6f176db9b2f1ccee1ef.jpg","keywords":"越野车违章777次","rcount":0,"time":1464741355000,"title":"越野车违章777次被扣4千分 罚款7万7(图)","topclass":1},{"count":12,"description":"原标题：启东一工厂车间发生爆炸扬子晚报讯（记者郭小川）5月31日下午5时53分，位于启东市的江苏海四达电源有限公司发生火灾爆炸事故","fcount":0,"fromname":"新浪新闻","fromurl":"http://news.sina.com.cn/o/2016-06-01/doc-ifxsqyku0145023.shtml","id":10403,"img":"/top/160601/c34a51efda15ef83537db0600582bfa4.jpg","keywords":"江苏启东发生爆炸","rcount":0,"time":1464741263000,"title":"启东一工厂车间 发生爆炸","topclass":1}]
     */

    private boolean status;
    private int total;
    /**
     * count : 23
     * description : 据信息时报报道今年31岁，已经是两个孩子的母亲，但她十多年以来的生活一直没有离开过父亲的“朋友圈”，她是原深圳政法委书记蒋尊玉的独生女蒋丹丹，被控与父亲共同收受贿赂现金7万元以及一辆价值约84万元的保时捷跑车，5月31日上午在广州中院过堂
     * fcount : 0
     * fromname : 中国青年网
     * fromurl : http://news.youth.cn/gn/201606/t20160601_8071924.htm
     * id : 10407
     * img : /top/160601/c7049c44f933783352ddbec2355074a4.jpg
     * keywords : 蒋尊玉女儿受审
     * rcount : 0
     * time : 1464748266000
     * title : 蒋尊玉女儿受审:从小到大 一路有人"示好照顾"
     * topclass : 1
     */

    private List<NewsBean> tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<NewsBean> getTngou() {
        return tngou;
    }

    public void setTngou(List<NewsBean> tngou) {
        this.tngou = tngou;
    }

    public static class NewsBean {
        private int count;
        private String description;
        private int fcount;
        private String fromname;
        private String fromurl;
        private int id;
        private String img;
        private String keywords;
        private int rcount;
        private long time;
        private String title;
        private int topclass;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFcount() {
            return fcount;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public String getFromname() {
            return fromname;
        }

        public void setFromname(String fromname) {
            this.fromname = fromname;
        }

        public String getFromurl() {
            return fromurl;
        }

        public void setFromurl(String fromurl) {
            this.fromurl = fromurl;
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

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
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

        public int getTopclass() {
            return topclass;
        }

        public void setTopclass(int topclass) {
            this.topclass = topclass;
        }
    }
}
