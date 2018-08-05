package gggroup.com.baron.authentication.signup

import android.content.Context
import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.AuthResponse
import gggroup.com.baron.utils.NetworkUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpPresenter(internal var view: SignUpContract.View) : SignUpContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun postUser(context: Context, username: String, phone: String, email: String, password: String) {
        if(NetworkUtil.isOnline(context)) {
            CallAPI.createService()
                    .postUser(username, email, password, phone)
                    .enqueue(object : Callback<AuthResponse> {
                        override fun onResponse(call: Call<AuthResponse>?, response: Response<AuthResponse>?) {
                            if (response?.body()?.status!="true")
                                view.onFailure("Tài khoản đã tồn tại")
                            else
                                view.onResponse(response)
                        }

                        override fun onFailure(call: Call<AuthResponse>?, t: Throwable?) {
                            view.onFailure(t?.message.toString())
                        }
                    })
        }
        else
            view.showNotification("Không có kết nối Internet")
    }
}