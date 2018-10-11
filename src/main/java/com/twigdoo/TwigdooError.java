package com.twigdoo;

public final class TwigdooError {

    private String message;
    private Integer messageCode;
    private TwigdooErrorDetails errors;


    public TwigdooError(String message) {
        this.message = message;
    }

    private TwigdooError() {

    }

    public String getMessage() {
        return message;
    }

    public Integer getMessageCode() {
        return messageCode;
    }

    public TwigdooErrorDetails getErrors() {
        return errors;
    }
}
