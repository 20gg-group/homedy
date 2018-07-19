package gggroup.com.baron.api

import gggroup.com.baron.entities.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface LinkAPI {

    @POST("v1/users/sign_up")
    @FormUrlEncoded
    fun postUser(@Field("username") username: String, @Field("phone") phone: String,
                 @Field("email") email: String, @Field("password") password: String) : Call<User>
}