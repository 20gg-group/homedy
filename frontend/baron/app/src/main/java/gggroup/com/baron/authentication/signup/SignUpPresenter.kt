package gggroup.com.baron.authentication.signup

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.AuthResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpPresenter(internal var view: SignUpContract.View) : SignUpContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun postUser(name: String, phone: String, email: String, password: String) {
        CallAPI.createService()
                .postUser(name, email, password, phone)
                .enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(call: Call<AuthResponse>?, response: Response<AuthResponse>?) {
                        view.onResponse(response)
                    }
                    override fun onFailure(call: Call<AuthResponse>?, t: Throwable?) {
                        view.onFailure(t)
                    }
                })
    }
}