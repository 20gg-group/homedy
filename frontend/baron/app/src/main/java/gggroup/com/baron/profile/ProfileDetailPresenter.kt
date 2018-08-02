package gggroup.com.baron.profile

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.ResultGetUser
import gggroup.com.baron.entities.UserPosts
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileDetailPresenter(internal var view: ProfileDetailContract.View):ProfileDetailContract.Presenter {
    override fun getUserPosts(Access_Token: String?,page:Int) {
        CallAPI.createService().getUserPosts(Access_Token,page)
                .enqueue(object :Callback<UserPosts>{
                    override fun onFailure(call: Call<UserPosts>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<UserPosts>?, response: Response<UserPosts>?) {
                        if (response != null) {
                            view.onResponseUserPosts(response.body()?.posts?.post)
                        }
                    }

                })
    }

    override fun getUser(Access_Token: String?) {
        CallAPI.createService().getUser(Access_Token)
                .enqueue(object : Callback<ResultGetUser> {
                    override fun onFailure(call: Call<ResultGetUser>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<ResultGetUser>?, response: Response<ResultGetUser>?) {
                        if (response != null) {
                            view.onResponse(response.body()!!)
                        }
                    }
                }
                )
    }

    override fun updateUser(Access_Token: String?, full_name: String, phone_number: String) {
        CallAPI.createService().updateUser(Access_Token,full_name,phone_number)
                .enqueue(object : Callback<ResultGetUser>
                {
                    override fun onFailure(call: Call<ResultGetUser>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<ResultGetUser>, response: Response<ResultGetUser>?) {
                        if (response != null) {
                            response.body()?.status
                        }
                    }

                })
    }

    override fun updateAvatar(Access_Token: String?,avatar: File) {
        val requestBody = RequestBody.create(
                MediaType.parse("image/*"),
                avatar
        )
        val file = MultipartBody.Part.createFormData("avatar", avatar.name, requestBody)
        CallAPI.createService().updateAvatar(Access_Token,file)
                .enqueue(object : Callback<ResultGetUser>
                {
                    override fun onFailure(call: Call<ResultGetUser>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<ResultGetUser>, response: Response<ResultGetUser>?) {
                        if (response != null) {
                            response.body()?.status
                        }
                    }

                })
    }
    init {
        view.setPresenter(this)
    }
}