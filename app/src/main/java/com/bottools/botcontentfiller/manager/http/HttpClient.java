package com.bottools.botcontentfiller.manager.http;


import com.bottools.botcontentfiller.model.ExportObject;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private static final String API_URL = "http://192.168.0.103:5000";

    private static ContentFillerRequest getRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ContentFillerRequest.class);
    }

    public static void simpleRequest(HandlerCallback<ResponseBody> callback) {
        ContentFillerRequest service = getRetrofit();
        Call<ResponseBody> call = service.getFaq();
        call.enqueue(callback);
    }

    public static void getData(HandlerCallback<ExportObject> callback) {
        ContentFillerRequest service = getRetrofit();
        Call<ExportObject> call = service.getData();
        call.enqueue(callback);
    }

    public static void postData(ExportObject data, HandlerCallback<ResponseBody> callback) {
        ContentFillerRequest service = getRetrofit();
        Call<ResponseBody> call = service.postData(data);
        call.enqueue(callback);
    }
}
