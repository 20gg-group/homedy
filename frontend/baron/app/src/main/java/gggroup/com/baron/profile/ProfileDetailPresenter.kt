package gggroup.com.baron.profile

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.ResultGetUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileDetailPresenter(internal var view: ProfileDetailContract.View):ProfileDetailContract.Presenter {
    override fun getUser(token: String) {
        CallAPI.createService().getUser(token)
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

    override fun updateUser(Access_Token: String, full_name: String, phone_number: String, avatar: File) {
        CallAPI.createService().updateUser(Access_Token,full_name,phone_number,avatar)
                .enqueue(object : Callback<ResultGetUser>
                {
                    override fun onFailure(call: Call<ResultGetUser>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<ResultGetUser>, response: Response<ResultGetUser>?) {
                        if (response != null) {
                            response.body()!!.status
                        }
                    }

                })
    }
    init {
        view.setPresenter(this)
    }
}