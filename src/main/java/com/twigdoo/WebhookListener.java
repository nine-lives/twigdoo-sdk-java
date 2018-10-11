package com.twigdoo;

public interface WebhookListener {
    default void created(Webhook<Lead> hook, Lead lead) {
    }

    default void updated(Webhook<Lead> hook, Lead lead) {
    }

    default void created(Webhook<Call> hook, Call call) {
    }

    default void updated(Webhook<Call> hook, Call call) {
    }

    default void created(Webhook<Sms> hook, Sms sms) {
    }

    default void updated(Webhook<Sms> hook, Sms sms) {
    }

    default void created(Webhook<Email> hook, Email email) {
    }

    default void updated(Webhook<Email> hook, Email email) {
    }

    default void error(Exception e, String payload) {

    }
}