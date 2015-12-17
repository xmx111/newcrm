package com.ufo.core.dao.exception;

/**
 * This class is used to handle the exception of object was not found.
 *
 */
public class ObjectNotFoundException extends DAOException {
    private static final long serialVersionUID = 6840487701302573847L;

    /**
	 * Create a new ObjectNotFoundException with the specified message.
	 * @param msg the detail message
	 */
	public ObjectNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Create a new ObjectNotFoundException with the specified message
	 * and root cause.
	 * @param msg the detail message
	 * @param ex the root cause
	 */
	public ObjectNotFoundException(String msg, Throwable ex) {
		super(msg, ex);
	}
	
	/**
     * Create a new ObjectNotFoundException with the specified message.
     * @param msg the detail message
     */
    public ObjectNotFoundException(String messageCode, Object[] messageArgs, String defaultMessage) {
        super(messageCode, messageArgs, defaultMessage);
    }

    /**
     * Create a new ObjectNotFoundException with the specified message
     * and root cause.
     * @param msg the detail message
     * @param ex the root cause
     */
    public ObjectNotFoundException(String messageCode, Object[] messageArgs, String defaultMessage, Throwable ex) {
        super(messageCode, messageArgs, defaultMessage, ex);
    }
}
