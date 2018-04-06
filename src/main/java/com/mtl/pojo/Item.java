package com.mtl.pojo;

public class Item {
    private String title;   //标题
    private String url;     //链接
    private String[] links; //百度云链接数组
    private String[] pwds;  //对应百度云链接密码
    private String linksAndPwdsStr; //百度云链接数组
    private String publishTime;     //发表时间
    private String authorityLevel;  //查看权限
    private String partition;       //帖子分区
    private String auther;          //帖子作者
    private String replyNum;        //回复数量
    private String viewNum;         //查看数量
    private String lastReplyName;   //最后回复账户
    private String lastReplyTime;   //最后回复时间
    private String lastReplyUrl;    //最后回复链接
    private String firstPageReply;  //第一页回复内容集合
    private boolean isNeedReply;    //是否需要回复才可以获取下载链接
    private int searchLinkTimes;    //搜寻链接次数, 以备后期超过阈值不在获取


    public String getLinksAndPwdsStr() {
        return linksAndPwdsStr;
    }

    public void setLinksAndPwdsStr(String linksAndPwdsStr) {
        if (linksAndPwdsStr == null || linksAndPwdsStr.equals("")){
            links = new String[]{};
            pwds = new String[]{};
        }else {
            String[] split = linksAndPwdsStr.split("#;#");
            links = split[0].split(";");
            pwds = split[1].split(";");
        }
        this.linksAndPwdsStr = linksAndPwdsStr;
    }

    public String[] getLinks() {
        return links;
    }

    public String[] getPwds() {
        return pwds;
    }

    public String getFirstPageReply() {
        return firstPageReply;
    }

    public void setFirstPageReply(String firstPageReply) {
        this.firstPageReply = firstPageReply;
    }

    public boolean isNeedReply() {
        return isNeedReply;
    }

    public void setNeedReply(boolean needReply) {
        isNeedReply = needReply;
    }

    public int getSearchLinkTimes() {
        return searchLinkTimes;
    }

    public void setSearchLinkTimes(int searchLinkTimes) {
        this.searchLinkTimes = searchLinkTimes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getAuthorityLevel() {
        return authorityLevel;
    }

    public void setAuthorityLevel(String authorityLevel) {
        this.authorityLevel = authorityLevel;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(String replyNum) {
        this.replyNum = replyNum;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public String getLastReplyName() {
        return lastReplyName;
    }

    public void setLastReplyName(String lastReplyName) {
        this.lastReplyName = lastReplyName;
    }

    public String getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(String lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public String getLastReplyUrl() {
        return lastReplyUrl;
    }

    public void setLastReplyUrl(String lastReplyUrl) {
        this.lastReplyUrl = lastReplyUrl;
    }
}
