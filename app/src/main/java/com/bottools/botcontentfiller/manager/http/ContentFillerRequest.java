/*
 * Copyright © ExpertOption Ltd. All rights reserved.
 */

package com.bottools.botcontentfiller.manager.http;

import com.bottools.botcontentfiller.model.ExportObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ContentFillerRequest {

    @GET("/")
    Call<ResponseBody> getFaq();

    @GET("/data")
    Call<ExportObject> getData();

    @POST("/data")
    Call<ResponseBody> postData(@Body ExportObject body);
}
