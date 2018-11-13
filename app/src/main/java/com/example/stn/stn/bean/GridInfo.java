package com.example.stn.stn.bean;

import java.io.Serializable;

/**
 * Name: GridInfo
 * Author: xulong
 * Comment: //方便主页面的图标跳转
 * Date: 2016-08-08 10:50.
 */
public class GridInfo implements Serializable {

    private String action;  //跳转页面
    private String label;   //标题
    private int icon;       //图标

    @Override
    public String toString() {
        return "GridInfo{" +
                "action='" + action + '\'' +
                ", label='" + label + '\'' +
                ", icon=" + icon +
                '}';
    }

    public GridInfo(String action, String label, int icon) {
        this.action = action;
        this.label = label;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
