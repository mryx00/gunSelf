package com.stylefeng.gunSelf.generator.action.model;

import java.io.Serializable;

/**
 * 自定义表字段
 */
public class MyTableField implements Serializable{
    private String propertyName;
    private String comment;
    private String type;
    private String style;
    private String valid;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }


}
