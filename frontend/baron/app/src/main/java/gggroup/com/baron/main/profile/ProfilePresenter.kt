package gggroup.com.baron.main.profile

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.ResultGetUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilePresenter(internal var view: ProfileContract.View) : ProfileContract.Presenter {
    override fun getUser(token: String) {
        CallAPI.createService()
                .getUserInfo(token)
                .enqueue(object : Callback<ResultGetUser> {
                    override fun onResponse(call: Call<ResultGetUser>?, response: Response<ResultGetUser>?) {
                        if (response?.body() != null) {
                            view.onResponse(response.body())
                        }
                    }

                    override fun onFailure(call: Call<ResultGetUser>?, t: Throwable?) {
                        view.onFailure(t?.message)
                    }
                })
    }

    init {
        view.setPresenter(this)
    }
}