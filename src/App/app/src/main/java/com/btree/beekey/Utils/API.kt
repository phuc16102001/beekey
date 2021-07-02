package com.btree.beekey.Utils

import com.btree.beekey.Model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface API {
    @POST("/account/login")
    fun postLogin(
        @Body loginBody: LoginPost
    ): Call<LoginResponse>

    @POST("/account/signup")
    fun postSignup(
        @Body signupBody: SignUpPost
    ): Call<SignUpResponse>

    @POST("/account/changePassword")
    fun postChangePassword(
        @Header("x-access-token") tokenHeader: String,
        @Body changePasswordBody: ChangePasswordPost
    ): Call<ChangePasswordResponse>

    @POST("/account/changeInformation")
    fun postChangeInformation(
        @Header("x-access-token") tokenHeader: String,
        @Body changeInformationBody: ChangeInformationPost
    ): Call<ChangeInformationResponse>
}
