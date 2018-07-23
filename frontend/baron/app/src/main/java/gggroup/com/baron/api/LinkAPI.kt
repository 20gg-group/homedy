package gggroup.com.baron.api

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import gggroup.com.baron.entities.AuthResponse
import gggroup.com.baron.entities.User
import retrofit2.Call
import retrofit2.http.*


interface LinkAPI {

    @POST("api/v1/auth/sign_up")
    @FormUrlEncoded
    fun postUser(@Field("username") username: String, @Field("phone") phone: String,
                 @Field("email") email: String, @Field("password") password: String) : Call<User>
    @POST("api/v1/auth/sign_in")
    @FormUrlEncoded
    fun checkUser(@Field("email") username: String, @Field("password") password: String): Call<AuthResponse>

    @POST("api/v1/auth/gg")
    @FormUrlEncoded
    fun push(@Field("access_token") username: String?): Call<GoogleSignInAccount>

    //Address
    @GET("api/city/{id}/district")
    fun getNews(@Path("id") id: Int): Call<String>

}