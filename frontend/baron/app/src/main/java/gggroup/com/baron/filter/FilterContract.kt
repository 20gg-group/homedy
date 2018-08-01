package gggroup.com.baron.filter

import java.util.*

interface FilterContract {
    interface View {
        fun showNotification(message: String)

        fun setPresenter(presenter: Presenter)

        fun onResponse(message: String)

        fun onFailure(message: String)

        fun setSpinnerDistrict(districts: LinkedList<String>)

        fun getUtils()

        fun show(isShow: Boolean)
    }

    interface Presenter {
        fun getDistrict(id: Int)

        fun getAllDistrict()
    }
}