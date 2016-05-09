package com.webservice.exception;

import org.apache.commons.lang.StringUtils;

public class UnexpectedProcessException extends ChainedUncheckedException {

    public UnexpectedProcessException(Throwable exception) {
        super(StringUtils.EMPTY, exception);
    }

    public UnexpectedProcessException(String msg, Throwable exception) {
        super(msg, exception);
    }

    public UnexpectedProcessException(String msg) {
        super(msg);
    }
}

