package com.jh.framework.ui.web;

import java.io.Serializable;

public class WebConfig implements Serializable {
    private String loadData;

    public WebConfig(String url) {
        loadData = url;
    }

    public String getLoadData() {
        return loadData;
    }
}
