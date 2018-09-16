package com.twigdoo;

import org.joda.time.DateTime;

import java.util.Map;

public class LeadResponse extends Lead {
    private long id;
    private DateTime createdOn;
    private DateTime updatedOn;
    private String stage;
    private Map<String, String> links;

    public long getId() {
        return id;
    }

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public DateTime getUpdatedOn() {
        return updatedOn;
    }

    public String getStage() {
        return stage;
    }
}
