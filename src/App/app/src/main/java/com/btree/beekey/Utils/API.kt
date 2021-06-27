package com.btree.beekey.Utils

import com.btree.beekey.Model.LoginPost
import com.btree.beekey.Model.LoginResponse
import com.btree.beekey.Model.SignUpPost
import com.btree.beekey.Model.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
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
