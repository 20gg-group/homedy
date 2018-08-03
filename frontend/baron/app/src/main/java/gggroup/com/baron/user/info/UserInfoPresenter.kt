package gggroup.com.baron.user.info

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.ResultGetUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoPresenter(internal var view: UserInfoContract.View):UserInfoContract.Presenter {
    override fun getUser(token: String) {
        CallAPI.createService().getUserInfo(token)
                .enqueue(object : Callback<ResultGetUser> {
                    override fun onFailure(call: Call<ResultGetUser>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<ResultGetUser>?, response: Response<ResultGetUser>?) {
                        response?.body()?.let { view.onResponse(it) }
                    }
                }
                )
    }
    init {
        view.setPresenter(this)
    }
}