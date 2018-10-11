package com.twigdoo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Map;

public class Webhook<T extends TwigdooEntity> {
    private long id;
    private int version;
    private TwigdooEntityType entity;
    private WebhookAction action;
    private DateTime timestamp;
    private T data;

    public long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public TwigdooEntityType getEntity() {
        return entity;
    }

    public WebhookAction getAction() {
        return action;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public T getData() {
        return data;
    }

    public static TwigdooEntityType findEntityType(ObjectMapper mapper, String payload) throws IOException {
        Map<String, Object> entity = mapper.readValue(payload, new TypeReference<Map<String, Object>>() { });
        return TwigdooEntityType.valueOf(String.valueOf(entity.get("entity")));
    }

    public static Webhook<Lead> getLead(ObjectMapper mapper, String payload) throws IOException {
        return mapper.readValue(payload, new TypeReference<Webhook<Lead>>() { });
    }

    public static Webhook<Call> getCall(ObjectMapper mapper, String payload) throws IOException {
        return mapper.readValue(payload, new TypeReference<Webhook<Call>>() { });
    }

    public static Webhook<Sms> getSms(ObjectMapper mapper, String payload) throws IOException {
        return mapper.readValue(payload, new TypeReference<Webhook<Sms>>() { });
    }

    public static Webhook<Email> getEmail(ObjectMapper mapper, String payload) throws IOException {
        return mapper.readValue(payload, new TypeReference<Webhook<Email>>() { });
    }
}
