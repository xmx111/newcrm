package com.ufo.core.common;

import java.util.List;

public class ValidationException extends BaseRuntimeException {

    /** 
    *serialVersionUID 
    */
    private static final long serialVersionUID = 1L;

    public ValidationException(String message, String messageCode) {
        super(message, messageCode);
    }

    public ValidationException(String msg, List<? extends Object> errors, Throwable ex) {
        super(msg, errors, ex);
    }

    public ValidationException(String msg, List<? extends Object> errors) {
        super(msg, errors);
    }

    public ValidationException(String messageCode, Object[] messageArgs, String defaultMessage,
            List<? extends Object> errors, Throwable ex) {
        super(messageCode, messageArgs, defaultMessage, errors, ex);
    }

    public ValidationException(String messageCode, Object[] messageArgs, String defaultMessage,
            List<? extends Object> errors) {
        super(messageCode, messageArgs, defaultMessage, errors);
    }

    public ValidationException(String messageCode, Object[] messageArgs, String defaultMessage, Throwable ex) {
        super(messageCode, messageArgs, defaultMessage, ex);
    }

    public ValidationException(String messageCode, Object[] messageArgs, String defaultMessage) {
        super(messageCode, messageArgs, defaultMessage);
    }

    public ValidationException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public ValidationException(String msg) {
        super(msg);
    }

}
