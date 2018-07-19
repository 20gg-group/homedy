package gggroup.com.baron.authentication.signup

import gggroup.com.baron.entities.User
import retrofit2.Response

interface SignUpContract {
    interface View {
        fun showNotification(message: String)

        fun setPresenter(presenter: Presenter)

        fun showLoading(isShow: Boolean)

        fun onResponse(response: Response<User>?)

        fun onFailure(t: Throwable?)
    }

    interface Presenter {
        fun postUser(username: String, phone: String, email: String, password: String)
    }
}