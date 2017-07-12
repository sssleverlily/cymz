package com.zia.gankcqupt_mvp.Bean;

/**
 * Created by zia on 17-7-12.
 */

public class Comment {
    private String userId;
    private String content;
    private String objectId;
    private String time;
    private boolean islz;//是楼主

    @Override
    public String toString() {
        return "userId:"+userId+"\n"+"content:"+content+"\n"+"objectId"+objectId+"\n"+"time:"+time+"\n"+"islz:"+islz;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean islz() {
        return islz;
    }

    public void setIslz(boolean islz) {
        this.islz = islz;
    }
}
