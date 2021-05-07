package com.auguigu.crowd.exception;

public class LoginAcctAlreadyInuseForupdateException extends RuntimeException{
    public LoginAcctAlreadyInuseForupdateException() {
        super();
    }

    public LoginAcctAlreadyInuseForupdateException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInuseForupdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInuseForupdateException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInuseForupdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
