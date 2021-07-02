package com.btree.beekey.Utils

import com.btree.beekey.Model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
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

    @POST("/account/changePassword")
    fun postchangepassword(
        @Header changepasswordheader: ChangePasswordHeader,
        @Body changepasswordbody: ChangePasswordPost
    ): Call<ChangePasswordResponse>

    @POST("/account/changeInformation")
    fun postchangeinformation(
        @Header changeinformationheader: ChangeInformationHeader,
        @Body changeinformationbody: ChangeInformationPost
    ): Call<ChangePasswordResponse>
}
