package gggroup.com.baron.user.info

import gggroup.com.baron.entities.ResultGetUser

interface UserInfoContract {
    interface View{
        fun setPresenter(presenter: Presenter)
        fun onResponse(resultGetUser: ResultGetUser)
    }
    interface Presenter{
        fun getUser(token:String)
    }
}