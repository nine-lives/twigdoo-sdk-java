package com.twigdoo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twigdoo.util.ObjectMapperFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebhookCallProcessor {
    private static final int DEFAULT_THREADS = 5;
    private final ObjectMapper mapper;
    private final ExecutorService executor;
    private final List<WebhookListener> listeners = new ArrayList<>();


    public WebhookCallProcessor() {
        this(DEFAULT_THREADS);
    }

    public WebhookCallProcessor(int threads) {
        this.executor = Executors.newFixedThreadPool(threads);
        this.mapper = ObjectMapperFactory.make();
    }

    public void addListener(WebhookListener listener) {
        listeners.add(listener);
    }

    public void removeListener(WebhookListener listener) {
        listeners.remove(listener);
    }

    public Future process(String payload) throws IOException {
        return executor.submit(() -> {
            try {
                TwigdooEntityType type = Webhook.findEntityType(mapper, payload);
                switch (type) {
                    case lead:
                        Webhook<Lead> leadhook = Webhook.getLead(mapper, payload);
                        fire(leadhook, leadhook.getData());
                        break;
                    case call:
                        Webhook<Call> callhook = Webhook.getCall(mapper, payload);
                        fire(callhook, callhook.getData());
                        break;
                    case sms:
                    default:
                        Webhook<Sms> smshook = Webhook.getSms(mapper, payload);
                        fire(smshook, smshook.getData());
                        break;
                }
            } catch (Exception e) {
                fire(e, payload);
            }
        });
    }

    private void fire(Exception e, String payload) {
        for (WebhookListener listener : listeners) {
            try {
                listener.error(e, payload);
            } catch (Exception ignore) {
            }
        }
    }

    private void fire(Webhook<Lead> hook, Lead data) {
        for (WebhookListener listener : listeners) {
            switch (hook.getAction()) {
                case created:
                    listener.created(hook, data);
                    break;
                case updated:
                default:
                    listener.updated(hook, data);
                    break;
            }
        }
    }

    private void fire(Webhook<Call> hook, Call data) {
        for (WebhookListener listener : listeners) {
            switch (hook.getAction()) {
                case created:
                    listener.created(hook, data);
                    break;
                case updated:
                default:
                    listener.updated(hook, data);
                    break;
            }
        }
    }

    private void fire(Webhook<Sms> hook, Sms data) {
        for (WebhookListener listener : listeners) {
            switch (hook.getAction()) {
                case created:
                    listener.created(hook, data);
                    break;
                case updated:
                default:
                    listener.updated(hook, data);
                    break;
            }
        }
    }
}
