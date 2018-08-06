package gggroup.com.baron.filter

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.District
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class FilterPresenter(internal var view: FilterContract.View) : FilterContract.Presenter {
    val hanoi: LinkedList<String> = LinkedList()
    val hochiminh: LinkedList<String> = LinkedList()
    init {
        view.setPresenter(this)
    }
    override fun getAllDistrict() {
        view.show(false)
        CallAPI.createService()
                .getDistrict(1)
                .enqueue(object : Callback<ArrayList<District>> {
                    override fun onResponse(call: Call<ArrayList<District>>?, response: Response<ArrayList<District>>?) {
                        val result = response?.body()
                        //val districts: LinkedList<String> = LinkedList()
                        if(result != null) {
                            for (i in 0 until result.size) {
                                hanoi.add(result[i].name)
                            }
                        }
                        view.setSpinnerDistrict(hanoi)
                        view.show(true)
                    }
                    override fun onFailure(call: Call<ArrayList<District>>?, t: Throwable?) {
                    }
                })
        CallAPI.createService()
                .getDistrict(2)
                .enqueue(object : Callback<ArrayList<District>> {
                    override fun onResponse(call: Call<ArrayList<District>>?, response: Response<ArrayList<District>>?) {
                        val result = response?.body()
                        if (result != null) {
                            for (i in 0 until result.size) {
                                hochiminh.add(result[i].name)
                            }
                        }
                    }
                    override fun onFailure(call: Call<ArrayList<District>>?, t: Throwable?) {
                    }
                })
    }

    override fun getDistrict(id: Int) {
        if(id == 0)
            view.setSpinnerDistrict(hanoi)
        else
            view.setSpinnerDistrict(hochiminh)
    }

//    override fun actionSearch(city: String, district: String, min_price: Float, max_price: Float, type: Int) {
//        CallAPI.createService().search(city,district,min_price,max_price,type)
//                .enqueue(object :Callback<AllPosts>{
//                    override fun onFailure(call: Call<AllPosts>?, t: Throwable?) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    }
//
//                    override fun onResponse(call: Call<AllPosts>?, response: Response<AllPosts>?) {
//                        view.onResponse(response?.body()?.posts?.post)
//                    }
//
//                })
//    }
}