package com.twigdoo;

public class TwigdooError {
    private String error;

    private TwigdooError() {

    }

    public TwigdooError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
