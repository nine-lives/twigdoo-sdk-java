package com.twigdoo;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class Sms implements TwigdooEntity {
    private long id;
    private long leadId;
    private SmsType smsType;
    private DateTime smsTime;
    private String contactNumber;
    private String content;
    private Map<String, String> links = new HashMap<>();

    public long getId() {
        return id;
    }

    public long getLeadId() {
        return leadId;
    }

    public SmsType getSmsType() {
        return smsType;
    }

    public DateTime getSmsTime() {
        return smsTime;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getContent() {
        return content;
    }

    public Map<String, String> getLinks() {
        return links;
    }
}
