package com.twigdoo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.Map;

public class Lead {
    private String sourceId;
    private String source;
    private Client client;

    private Service service;

    private String status;
    private String lostReason;
    private LocalDateTime lostOn;
    private LocalDateTime deletedOn;
    private LocalDateTime qualifiedOn;
    private Map<String, Object> data;

    public String getSourceId() {
        return sourceId;
    }

    public Lead withSourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public String getSource() {
        return source;
    }

    public Lead withSource(String source) {
        this.source = source;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public Lead withClient(Client client) {
        this.client = client;
        return this;
    }

    public Service getService() {
        return service;
    }

    public Lead withService(Service service) {
        this.service = service;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Lead withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getLostReason() {
        return lostReason;
    }

    public Lead withLostReason(String lostReason) {
        this.lostReason = lostReason;
        return this;
    }

    public DateTime getLostOn() {
        return lostOn.toDateTime(DateTimeZone.UTC);
    }

    public Lead withLostOn(DateTime lostOn) {
        this.lostOn = lostOn.withZone(DateTimeZone.UTC).toLocalDateTime();
        return this;
    }

    public DateTime getDeletedOn() {
        return deletedOn.toDateTime(DateTimeZone.UTC);
    }

    public Lead withDeletedOn(DateTime deletedOn) {
        this.deletedOn = deletedOn.withZone(DateTimeZone.UTC).toLocalDateTime();
        return this;
    }

    public DateTime getQualifiedOn() {
        return qualifiedOn.toDateTime(DateTimeZone.UTC);
    }

    public Lead withQualifiedOn(DateTime qualifiedOn) {
        this.qualifiedOn = qualifiedOn.withZone(DateTimeZone.UTC).toLocalDateTime();
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Lead withData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

}
