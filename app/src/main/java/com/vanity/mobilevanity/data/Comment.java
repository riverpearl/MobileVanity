package com.vanity.mobilevanity.data;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class Comment implements AlertItem {
    private long id;
    private User writer;
    private long BeautyTipId;
    private String content;
    private String writeDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public long getBeautyTipId() {
        return BeautyTipId;
    }

    public void setBeautyTipId(long beautyTipId) {
        BeautyTipId = beautyTipId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
}
