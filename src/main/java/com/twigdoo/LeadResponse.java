package com.twigdoo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.Map;

public class LeadResponse extends Lead {
    private long id;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String stage;
    private Map<String, String> links;

    public long getId() {
        return id;
    }

    public DateTime getCreatedOn() {
        return createdOn.toDateTime(DateTimeZone.UTC);
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public DateTime getUpdatedOn() {
        return updatedOn.toDateTime(DateTimeZone.UTC);
    }

    public String getStage() {
        return stage;
    }
}
