package com.example.nicc.my_cms.Instance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NICC on 2022-03-17.
 */

public class RetrofitClientInstance {

    private static Retrofit retrofit;

    private static final String BASE_URL = "https://gits.github.com/jsturgis";

    static Gson gson = new GsonBuilder().setLenient().create();

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
