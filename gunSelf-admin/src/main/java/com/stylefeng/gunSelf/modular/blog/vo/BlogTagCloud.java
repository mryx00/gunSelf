package com.stylefeng.gunSelf.modular.blog.vo;

public class BlogTagCloud {
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    String tagName;
    int count;
}
