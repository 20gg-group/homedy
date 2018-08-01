package gggroup.com.baron.main.post

import android.support.v7.widget.DialogTitle
import com.esafirm.imagepicker.model.Image
import gggroup.com.baron.entities.District
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

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

            fun post()
        }

        interface Presenter {
            fun getDistrict(id: Int)

            fun getAllDistrict()

            fun post(title: String, price: Float, area: Float, description: String, phone: String,
                     type_house: Int, utils: ArrayList<String>, city : String, district: String, address: String, files: ArrayList<File>?)
        }
}