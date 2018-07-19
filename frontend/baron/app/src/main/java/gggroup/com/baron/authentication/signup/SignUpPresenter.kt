package gggroup.com.baron.authentication.signup

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpPresenter(internal var view: SignUpContract.View) : SignUpContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun postUser(username: String, phone: String, email: String, password: String) {
        CallAPI.createService()
                .postUser(username, phone, email, password)
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>?, response: Response<User>?) {
                        view.onResponse(response)
                    }
                    override fun onFailure(call: Call<User>?, t: Throwable?) {
                        view.onFailure(t)
                    }
                })
    }
}