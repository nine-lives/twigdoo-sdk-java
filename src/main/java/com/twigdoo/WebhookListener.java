package com.twigdoo;

public interface WebhookListener {
    default void created(Webhook<Lead> hook, Lead lead) {
    }

    default void updated(Webhook<Lead> hook, Lead lead) {
    }

    default void created(Webhook<Call> hook, Call lead) {
    }

    default void updated(Webhook<Call> hook, Call lead) {
    }

    default void created(Webhook<Sms> hook, Sms lead) {
    }

    default void updated(Webhook<Sms> hook, Sms lead) {
    }

    default void error(Exception e, String payload) {

    }
}