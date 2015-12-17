package com.ufo.core.dao.exception;

import com.ufo.core.common.BaseRuntimeException;

/**
 * This class is used to handle all the exceptions of DAO layer.
 *
 */
public class DAOException extends BaseRuntimeException {
    private static final long serialVersionUID = 2141179128538896102L;

    /**
     * Create a new DAOException with the specified message.
     * @param msg the detail message
     */
    public DAOException(String msg) {
        super(msg);
    }

    /**
     * Create a new DAOException with the specified message
     * and root cause.
     * @param msg the detail message
     * @param ex the root cause
     */
    public DAOException(String msg, Throwable ex) {
        super(msg, ex);
    }


    /**
     * Create a new DAOException with the specified message.
     * @param msg the detail message
     */
    public DAOException(String messageCode, Object[] messageArgs, String defaultMessage) {
        
        super(messageCode, messageArgs, defaultMessage);
    }

    /**
     * Create a new DAOException with the specified message
     * and root cause.
     * @param msg the detail message
     * @param ex the root cause
     */
    public DAOException(String messageCode, Object[] messageArgs, String defaultMessage, Throwable ex) {
        super(messageCode, messageArgs, defaultMessage, ex);
    }
}
