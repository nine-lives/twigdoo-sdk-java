package com.twigdoo;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class Email implements TwigdooEntity {
    private long id;
    private long leadId;
    private EmailType emailType;
    private String to;
    private String from;
    private String subject;
    private String summaryBody;
    private DateTime sent;
    private DateTime received;
    private String messageId;
    @JsonAlias("_links")
    private Map<String, String> links = new HashMap<>();

    @Override
    public long getId() {
        return id;
    }

    public long getLeadId() {
        return leadId;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getSummaryBody() {
        return summaryBody;
    }

    public DateTime getSent() {
        return sent;
    }

    public DateTime getReceived() {
        return received;
    }

    public String getMessageId() {
        return messageId;
    }

    @Override
    public Map<String, String> getLinks() {
        return links;
    }
}
