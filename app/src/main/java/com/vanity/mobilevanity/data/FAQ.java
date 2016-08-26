package com.vanity.mobilevanity.data;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class FAQ implements ParentListItem {
    private long id;
    private String title;
    private String content;
    private List<String> child = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChild(List<String> child) {
        this.child.addAll(child);
    }

    @Override
    public List<?> getChildItemList() {
        return child;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
