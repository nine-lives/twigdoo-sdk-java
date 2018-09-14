package com.twigdoo.util;

public final class QueryEscaper {
    private static final String ESCAPABLE_CHARS = "([+-=!()\\{\\}\\[\\]^\\\\\"~*?:\\/]|&&|\\|\\|)";
    private static final String INVALID_CHARS = "([\\<\\>])";

    private QueryEscaper() {

    }

    public static String escape(String query) {
        if (query == null) {
            return null;
        }

        return query.replaceAll(INVALID_CHARS, "").replaceAll(ESCAPABLE_CHARS, "\\\\$1");
    }
}
