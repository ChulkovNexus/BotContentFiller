/*
 * Copyright © ExpertOption Ltd. All rights reserved.
 */

package com.bottools.botcontentfiller.manager.http;

public class ErrorEntity {
    public static final int ERROR_CODE_TIMEOUT = 0x1001;
    public static final int ERROR_NO_INTERNET = 0x1002;
    public static final int ERROR_CODE_NO_CONTENT = 0x1003;
    public static final int ERROR_UNKNOUN_EXEPTION = 0x1004;
    public static final int ERROR_CODE_NOT_FOUND = 0x1005;
    public static final int ERROR_CODE_BAD_REQUEST = 0x1006;
    public static final int ERROR_CODE_SERVER_EXCEPTION = 0x1007;
    public static final int ERROR_CODE_METHOD_NOT_ALLOWED = 0x1008;
    public static final int ERROR_AUTH_NEEDED = 1;

    private int errorCode;
    private String errorDesc = "";

    public ErrorEntity(){
    }

    public ErrorEntity(int errorCode){
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public boolean isError() {
        return errorCode>0;
    }
}