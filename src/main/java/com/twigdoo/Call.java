package com.twigdoo;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class Call implements TwigdooEntity {
    private long id;
    private long leadId;
    private CallType callType;
    private String contactNumber;
    private DateTime callTime;
    private int duration;
    private String notes;
    private Map<String, String> links = new HashMap<>();

    public long getId() {
        return id;
    }

    public long getLeadId() {
        return leadId;
    }

    public CallType getCallType() {
        return callType;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getDuration() {
        return duration;
    }

    public DateTime getCallTime() {
        return callTime;
    }

    public String getNotes() {
        return notes;
    }

    public Map<String, String> getLinks() {
        return links;
    }
}
