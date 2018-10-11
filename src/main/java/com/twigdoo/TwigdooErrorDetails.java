package com.twigdoo;

import java.util.List;

public final class TwigdooErrorDetails {

    private List<String> missing;
    private List<String> invalid;

    private TwigdooErrorDetails() {
    }

    public List<String> getMissing() {
        return missing;
    }

    public List<String> getInvalid() {
        return invalid;
    }
}
