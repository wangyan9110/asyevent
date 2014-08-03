package org.yywang.asyevent.exception;

/**
 * Created by wangyan on 14-8-3.
 */
public class AsyEventException extends RuntimeException {

    private String message;

    public AsyEventException(String message){
        super(message);
    }

    public AsyEventException(String message,Throwable e){
        super(message,e);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
