package gggroup.com.baron.api

import gggroup.com.baron.entities.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import java.io.File


interface LinkAPI {

    @POST("api/v1/auth/sign_up")
    @FormUrlEncoded
    fun postUser(@Field("full_name") full_name: String, @Field("email") email: String,
                 @Field("password") password: String, @Field("phone_number") phone_number: String) : Call<AuthResponse>
    @POST("api/v1/auth/sign_in")
    @FormUrlEncoded
    fun checkUser(@Field("email") email: String, @Field("password") password: String): Call<AuthResponse>

    @POST("api/v1/auth/gg")
    @FormUrlEncoded
    fun push(@Field("full_name") full_name: String, @Field("email") email: String,
             @Field("phone_number") phone_number: String) : Call<AuthResponse>

    @GET("api/v1/city/{id}/district")
    fun getDistrict(@Path("id") id: Int): Call<ArrayList<District>>

    @GET("api/v1/users")
    fun getUser( @Header("Access-Token") Access_Token:String):Call<ResultGetUser>

    @PUT ("api/v1/users")
    @FormUrlEncoded
    fun updateUser(@Header("Access-Token")Access_Token:String, @Field("full_name") full_name: String, @Field("phone_number") phone_number: String
                  ):Call<ResultGetUser>
}