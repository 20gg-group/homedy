package gggroup.com.baron.main.profile


import gggroup.com.baron.entities.ResultGetUser
import gggroup.com.baron.entities.UserFrofile
import java.io.File

interface ProfileContract {
    interface View{
        fun setPresenter(presenter: Presenter)
        fun onResponse(resultGetUser: ResultGetUser)
    }
    interface Presenter{
        fun getUser()
        fun updateUser(full_name: String,phone_number: String)
    }
}