package gggroup.com.baron.main.profile

import gggroup.com.baron.entities.ResultGetUser

interface ProfileContract {

    interface View {
        fun setPresenter(presenter: Presenter)

        fun onResponse(resultGetUser: ResultGetUser?)

        fun showNotification(message: String?)

        fun onFailure(message: String?)
    }

    interface Presenter {
        fun getUser(token: String)
    }
}