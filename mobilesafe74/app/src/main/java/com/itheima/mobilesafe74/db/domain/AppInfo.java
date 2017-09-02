package com.itheima.mobilesafe74.db.domain;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/9/2.
 */

public class AppInfo {

    // 名称，包名，图标，（内存，sd卡），（系统，用户）
    public String name;
    public String packName;
    public Drawable icon;
    public boolean isSdCard;
    public boolean isSystem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSdCard() {
        return isSdCard;
    }

    public void setSdCard(boolean sdCard) {
        isSdCard = sdCard;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }


}
