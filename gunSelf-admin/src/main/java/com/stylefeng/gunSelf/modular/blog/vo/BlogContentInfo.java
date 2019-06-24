package com.stylefeng.gunSelf.modular.blog.vo;

import com.stylefeng.gunSelf.modular.system.model.BlogTag;

import java.util.Date;
import java.util.List;

public class BlogContentInfo {
    private int id;
    private Date date;
    private String content;
    private String caption;
    private String auther;

    public List<BlogTag> getBlogTags() {
        return blogTags;
    }

    public void setBlogTags(List<BlogTag> blogTags) {
        this.blogTags = blogTags;
    }

    private int CommentSum;
    private int TopSum;
    private int count;
    private String topicName;

    private List<BlogTag> blogTags;


    public int getId() {
        return id;
    }

    public int getTopSum() {
        return TopSum;
    }

    public void setTopSum(int topSum) {
        TopSum = topSum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getCommentSum() {
        return CommentSum;
    }

    public void setCommentSum(int commentSum) {
        CommentSum = commentSum;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }


    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
