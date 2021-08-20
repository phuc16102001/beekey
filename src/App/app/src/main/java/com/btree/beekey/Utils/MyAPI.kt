package com.btree.beekey.Utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAPI {
    companion object {
        val BASE_URL = "http://115.79.226.47"
        val PORT = "8080"

        fun getAPI(): API {
            return Retrofit.Builder()
                .baseUrl(BASE_URL + ":" + PORT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
        }
    }
}