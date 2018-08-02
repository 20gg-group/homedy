package gggroup.com.baron.user.info

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.ResultGetUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoPresenter(internal var view: UserInfoContract.View):UserInfoContract.Presenter {
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
    init {
        view.setPresenter(this)
    }
}