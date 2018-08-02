package gggroup.com.baron.api

import gggroup.com.baron.entities.AuthResponse
import gggroup.com.baron.entities.BaseResponse
import gggroup.com.baron.entities.District
import okhttp3.MultipartBody
import okhttp3.RequestBody
import gggroup.com.baron.entities.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.Multipart
import java.io.File


interface LinkAPI {

    @POST("api/v1/users/sign_up")
    @FormUrlEncoded
    fun postUser(@Field("full_name") full_name: String, @Field("email") email: String,
                 @Field("password") password: String, @Field("phone_number") phone_number: String) : Call<AuthResponse>
    @POST("api/v1/users/sign_in")
    @FormUrlEncoded
    fun checkUser(@Field("email") email: String, @Field("password") password: String): Call<AuthResponse>

    @POST("api/v1/user/sign_in_with_google")
    @FormUrlEncoded
    fun signInWithGoogle(@Field("full_name") full_name: String, @Field("email") email: String) : Call<AuthResponse>

    @GET("api/v1/city/{id}/district")
    fun getDistrict(@Path("id") id: Int): Call<ArrayList<District>>

    @POST("api/v1/posts")
    @Multipart
    fun post(@Header("Access-Token" ) token: String?, @Part("post[title]") title: RequestBody ,
             @Part("post[price]") price: Float, @Part("post[area]") area: Float,
             @Part("post[description]") description: RequestBody , @Part("post[phone_contact_number]") phone: RequestBody ,
             @Part("post[type_house]") type: Int, @Part("post[detail_ids][]") utils: Array<RequestBody?>,
             @Part("address[city]") city: RequestBody , @Part("address[district]") district: RequestBody ,
             @Part("address[add_detail]") address: RequestBody , @Part file: Array<MultipartBody.Part?>) : Call<BaseResponse>

    @GET("api/v1/users")
    fun getUser( @Header("Access-Token") Access_Token:String?):Call<ResultGetUser>

    @PUT ("api/v1/users")
    @FormUrlEncoded
    fun updateUser(@Header("Access-Token")Access_Token:String?, @Field("full_name") full_name: String, @Field("phone_number") phone_number: String
                  ):Call<ResultGetUser>
//
    @GET("api/v1/posts")
    fun getAllPosts() : Call<AllPosts>

    @GET("api/v1/posts/{id}")
    fun getDetailPost(@Path("id") id: Int) : Call<DetailPost>

    @GET("api/v1/search/search_multi")
    fun search(@Query("city") city: String, @Query("district") district: String,
               @Query("min_price") min_price: Float, @Query("max_price") max_price: Float,
               @Query("type_house") type_house: Int): Call<AllPosts>
}