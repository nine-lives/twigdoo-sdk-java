package com.twigdoo;

import org.joda.time.DateTime;

import java.util.Map;

public class LeadRequest {
    private String sourceId;
    private String source;
    private Client client;

    private Service service;

    private String status;
    private String lostReason;
    private DateTime lostOn;
    private DateTime deletedOn;
    private DateTime qualifiedOn;
    private Map<String, Object> data;

    public String getSourceId() {
        return sourceId;
    }

    public LeadRequest withSourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public String getSource() {
        return source;
    }

    public LeadRequest withSource(String source) {
        this.source = source;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public LeadRequest withClient(Client client) {
        this.client = client;
        return this;
    }

    public Service getService() {
        return service;
    }

    public LeadRequest withService(Service service) {
        this.service = service;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public LeadRequest withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getLostReason() {
        return lostReason;
    }

    public LeadRequest withLostReason(String lostReason) {
        this.lostReason = lostReason;
        return this;
    }

    public DateTime getLostOn() {
        return lostOn;
    }

    public LeadRequest withLostOn(DateTime lostOn) {
        this.lostOn = lostOn;
        return this;
    }

    public DateTime getDeletedOn() {
        return deletedOn;
    }

    public LeadRequest withDeletedOn(DateTime deletedOn) {
        this.deletedOn = deletedOn;
        return this;
    }

    public DateTime getQualifiedOn() {
        return qualifiedOn;
    }

    public LeadRequest withQualifiedOn(DateTime qualifiedOn) {
        this.qualifiedOn = qualifiedOn;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public LeadRequest withData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

}
