package com.twigdoo;

public class TwigdooException extends RuntimeException {

    public TwigdooException(String message) {
        super(message);
    }

    public TwigdooException(Throwable e) {
        super(e);
    }
}
