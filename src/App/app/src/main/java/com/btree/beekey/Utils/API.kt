package com.btree.beekey.Utils

import com.btree.beekey.Controller.Adapter.Category
import com.btree.beekey.Model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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

    @GET("/account/getInformation")
    fun getInformation(
        @Header("x-access-token") tokenHeader: String
    ): Call<GetInformationResponse>

    @GET("/category")
    fun getCategoryList(
        @Header("x-access-token") tokenHeader: String
    ): Call<CategoryResponse>
  
    @POST("/account/topUp")
    fun postTopUp(
        @Header("x-access-token") tokenHeader: String,
        @Body topUpBody: TopUpBody
    ): Call<TopUpResponse>

    @GET("/task/getMyRequest")
    fun getMyRequest(
        @Header("x-access-token") tokenHeader: String
    ):Call<ListTaskResponse>

    @GET("/task/getMyTask")
    fun getMyTask(
        @Header("x-access-token") tokenHeader: String
    ):Call<ListTaskResponse>

    @POST("/counterOffer/post")
    fun postCounterOffer(
        @Header("x-access-token") tokenHeader: String,
        @Body counterOfferBody: CounterOfferPost
    ): Call<CounterOfferResponse>

    @POST("/task/getByCategory")
    fun postTaskByCategory(
        @Header("x-access-token") tokenHeader: String,
        @Body categoryBody: Category
    ): Call<ListTaskResponse>

    @POST("/report")
    fun postReport(
        @Header("x-access-token") tokenHeader: String,
        @Body reportBody: ReportPost
    ): Call<ReportResponse>
  
    @POST("/task/post")
    fun postPostTask(
        @Header("x-access-token") tokenHeader: String,
        @Body postTaskBody: PostTaskPost
    ): Call<PostTaskResponse>
}
