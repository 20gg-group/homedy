package gggroup.com.baron.updateinfo

import gggroup.com.baron.entities.ResultGetUser

interface UpdateInfoContract {
    interface View{
        fun setPresenter(presenter: Presenter)
        fun onResponse(resultGetUser: ResultGetUser)
    }
    interface Presenter{
        fun updateUser(Access_Token:String,full_name: String,phone_number: String)
    }
}