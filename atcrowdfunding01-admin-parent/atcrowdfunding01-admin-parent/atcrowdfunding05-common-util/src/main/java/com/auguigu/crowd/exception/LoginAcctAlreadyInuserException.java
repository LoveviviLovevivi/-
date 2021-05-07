package com.auguigu.crowd.exception;

/**
 * 保存或更新admin时如果检测到账号重复，则抛出这个异常
 * @author
 */
public class LoginAcctAlreadyInuserException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public LoginAcctAlreadyInuserException() {
        super();
    }

    public LoginAcctAlreadyInuserException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInuserException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInuserException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInuserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
