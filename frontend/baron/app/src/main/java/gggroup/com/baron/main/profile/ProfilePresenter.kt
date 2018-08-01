package gggroup.com.baron.main.profile

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.authentication.signin.SignInActivity
import gggroup.com.baron.entities.ResultGetUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfilePresenter(internal var view: ProfileContract.View):ProfileContract.Presenter {
    override fun updateUser(full_name: String, phone_number: String) {
        CallAPI.createService().updateUser(SignInActivity.TOKEN,full_name,phone_number)
                .enqueue(object : Callback<ResultGetUser>
                {
                    override fun onFailure(call: Call<ResultGetUser>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<ResultGetUser>?, response: Response<ResultGetUser>?) {
                        if (response != null) {
                             response.body()?.status
                        }
                    }

                })
    }

    override fun getUser() {
        CallAPI.createService().getUser(SignInActivity.TOKEN)
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