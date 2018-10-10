/*
 * Copyright © ExpertOption Ltd. All rights reserved.
 */

package com.bottools.botcontentfiller.manager.http;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class HandlerCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            if (response.body() == null) {
                ErrorEntity errorEntity = new ErrorEntity(ErrorEntity.ERROR_CODE_NO_CONTENT);
                onError(errorEntity);
            } else {
                onComplite(response.body());
            }
        } else {
            ErrorEntity errorEntity;
            if (response.code() == 404) {
                errorEntity = new ErrorEntity(ErrorEntity.ERROR_CODE_NOT_FOUND);
            } else if (response.code() == 400) {
                errorEntity = new ErrorEntity(ErrorEntity.ERROR_CODE_BAD_REQUEST);
            } else if (response.code() == 405) {
                errorEntity = new ErrorEntity(ErrorEntity.ERROR_CODE_METHOD_NOT_ALLOWED);
            } else if (response.code() == 401) {
                errorEntity = new ErrorEntity(ErrorEntity.ERROR_AUTH_NEEDED);
            } else if (response.code() >= 500) {
                errorEntity = new ErrorEntity(ErrorEntity.ERROR_CODE_SERVER_EXCEPTION);
            } else if (response.code() >= 422) {
                errorEntity = new ErrorEntity();
                try {
                    errorEntity.setErrorDesc(response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                errorEntity = new ErrorEntity(ErrorEntity.ERROR_UNKNOUN_EXEPTION);
            }
            onError(errorEntity);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        ErrorEntity errorEntity;
        if (t instanceof SocketTimeoutException) {
            errorEntity = new ErrorEntity(ErrorEntity.ERROR_CODE_TIMEOUT);
        } else if (t instanceof UnsupportedOperationException && t.getMessage().equals("JsonNull")) {
            errorEntity = new ErrorEntity(ErrorEntity.ERROR_CODE_NO_CONTENT);
        } else if (t instanceof UnknownHostException) {
            errorEntity = new ErrorEntity(ErrorEntity.ERROR_NO_INTERNET);
        } else {
            errorEntity = new ErrorEntity(ErrorEntity.ERROR_UNKNOUN_EXEPTION);
            t.printStackTrace();
        }

        onError(errorEntity);
    }

    public abstract void onError(ErrorEntity error);
    public abstract void onComplite(T result);
}
