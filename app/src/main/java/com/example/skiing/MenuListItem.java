package com.example.skiing;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class MenuListItem {
    private String text;
    private Drawable icon;

    public MenuListItem(String t, Drawable f) {
        this.text = t;
        this.icon = f;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
