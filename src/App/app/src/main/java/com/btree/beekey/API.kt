package com.btree.beekey

import com.btree.beekey.api.LoginPost
import com.btree.beekey.api.LoginResponse
import com.btree.beekey.api.SignUpPost
import com.btree.beekey.api.SignUpResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface API {
    @POST("/account/login")
    fun postlogin(
       @Body login : LoginPost
    ): Call<LoginResponse>

    @POST("/account/signup")
    fun postsignup(
        @Body signup : SignUpPost
    ): Call<SignUpResponse>
}