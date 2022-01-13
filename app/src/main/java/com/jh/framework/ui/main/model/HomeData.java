package com.jh.framework.ui.main.model;

import java.io.Serializable;
import java.util.List;

public class HomeData implements Serializable {
    List<Issue> issueList;
    String nextPageUrl;
    long nextPublishTime;
    String newestIssueType;
    String dialog;


    public List<Issue> getIssueList() {
        return issueList;
    }

    public static class Issue {
        long releaseTime;
        String type;
        long date;
        int total;
        long publishTime;
        List<Item> itemList;
        int count;
        String nextPageUrl;

        public List<Item> getItemList() {
            return itemList;
        }
    }

    public static class Item {
        String type;
        Data data;
        String tag;

        public String getImage() {


            if (type.startsWith("banner")) {
                return data.getImage();
            } else {
                return data.cover.detail;
            }
        }

    }

    public static class Data {

        private String image;
        private Cover cover;

        public String getImage() {
            return image;
        }
    }

    public static class Cover {
        private String detail;
    }
}
