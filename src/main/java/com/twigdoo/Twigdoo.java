package com.twigdoo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.twigdoo.client.HttpClient;

import java.util.List;

/**
 * Twigdoo SDK entry point
 */
public final class Twigdoo {
    private final HttpClient client;

    private Twigdoo(Configuration configuration) {
        this.client = new HttpClient(configuration);
    }

    /**
     * Get a Twigdoo instance for your given api key.
     *
     * @param apiKey your client id
     * @return a Twigdoo instance
     */
    public static Twigdoo make(String apiKey) {
        return new Twigdoo(new Configuration()
                .withApiKey(apiKey));
    }

    /**
     * Get a Twigdoo instance using finer grained control over configuration.
     *
     * @param configuration your configuration
     * @return a Twigdoo instance
     */
    public static Twigdoo make(Configuration configuration) {
        return new Twigdoo(configuration);
    }

    public LeadResponse get(long id) {
        return client.get("lead/" + id, null, LeadResponse.class);
    }

    public LeadResponse get(String sourceId) {
        return client.get("lead/source-id/" + sourceId, null, LeadResponse.class);
    }

    public List<Call> calls(long id) {
        return client.get("lead/" + id + "/calls", null, new TypeReference<List<Call>>() { });
    }

    public void delete(long id) {
        client.delete("lead/" + id, null, null);
    }

    public LeadResponse delete(String sourceId) {
        return client.delete("lead/source-id/" + sourceId, null, LeadResponse.class);
    }

    public LeadResponse create(Lead lead) {
        return client.post("lead", lead, LeadResponse.class);
    }

    public LeadResponse update(long id, Lead lead) {
        return client.put("lead/" + id, lead, LeadResponse.class);
    }

    public LeadResponse update(Lead lead) {
        return client.put("lead/source-id/" + lead.getSourceId(), lead, LeadResponse.class);
    }

    public LeadResponse patch(long id, Lead lead) {
        return client.patch("lead/" + id, lead, LeadResponse.class);
    }

    public LeadResponse patch(Lead lead) {
        return client.patch("lead/source-id/" + lead.getSourceId(), lead, LeadResponse.class);
    }
}
