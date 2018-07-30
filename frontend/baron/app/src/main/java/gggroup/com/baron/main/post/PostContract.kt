package gggroup.com.baron.main.post

import com.esafirm.imagepicker.model.Image
import java.util.*

interface PostContract {
        interface View {
            fun showNotification(message: String)

            fun setPresenter(presenter: Presenter)

            fun getImage()

            fun onResponse(message: String)

            fun onFailure(message: String)

            fun displayImg(images: ArrayList<Image>?)

            fun setSpinnerDistrict(districts: LinkedList<String>)

            fun getUtils()

            fun show(isShow: Boolean)

            fun getType()
        }

        interface Presenter {
            fun getDistrict(id: Int)

            fun getAllDistrict()
        }
}