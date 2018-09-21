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

    public Lead get(long id) {
        return client.get("lead/" + id, null, Lead.class);
    }

    public Lead get(String sourceId) {
        return client.get("lead/source-id/" + sourceId, null, Lead.class);
    }

    public List<Call> calls(long id) {
        return client.get("lead/" + id + "/calls", null, new TypeReference<List<Call>>() { });
    }

    public List<Sms> smses(long id) {
        return client.get("lead/" + id + "/smses", null, new TypeReference<List<Sms>>() { });
    }

    public void delete(long id) {
        client.delete("lead/" + id, null, null);
    }

    public Lead delete(String sourceId) {
        return client.delete("lead/source-id/" + sourceId, null, Lead.class);
    }

    public Lead create(LeadRequest lead) {
        return client.post("lead", lead, Lead.class);
    }

    public Lead update(long id, LeadRequest lead) {
        return client.put("lead/" + id, lead, Lead.class);
    }

    public Lead update(LeadRequest lead) {
        return client.put("lead/source-id/" + lead.getSourceId(), lead, Lead.class);
    }

    public Lead patch(long id, LeadRequest lead) {
        return client.patch("lead/" + id, lead, Lead.class);
    }

    public Lead patch(LeadRequest lead) {
        return client.patch("lead/source-id/" + lead.getSourceId(), lead, Lead.class);
    }
}
