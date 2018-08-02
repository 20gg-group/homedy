@file:Suppress("DEPRECATION")

package gggroup.com.baron.filter

import android.app.ActivityOptions
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import gggroup.com.baron.R
import java.util.*
import java.util.Arrays.asList
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import gggroup.com.baron.main.post.PostFragment
import kotlinx.android.synthetic.main.activity_filter.*
import com.jaygoo.widget.RangeSeekBar
import com.jaygoo.widget.OnRangeChangedListener
import gggroup.com.baron.entities.OverviewPost
import gggroup.com.baron.posts.ListPostActivity
import kotlinx.android.synthetic.main.item_image.*
import java.text.DecimalFormat
import kotlin.collections.ArrayList


class FilterActivity : AppCompatActivity(),FilterContract.View {
    private var presenter: FilterContract.Presenter? = null
    private var types: BooleanArray = booleanArrayOf(false,false)
    private var min_price: Float = 0F
    private var max_price: Float = 12F
    private var check_utils: BooleanArray = booleanArrayOf(false,false,false,false,
            false,false,false,false,
            false,false,false,false,
            false,false,false,false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        presenter = FilterPresenter(this)
        //setToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener({
            onBackPressed()
            this.overridePendingTransition(0,R.anim.back_right)
            finish()
        })
        val sex = LinkedList(asList("Nam", "Nữ", "Cả 2"))
        spinnerSex.attachDataSource(sex)
        val city = LinkedList(asList("Hà Nội", "Hồ Chí Minh"))
        spinnerProvince.attachDataSource(city)
        presenter?.getAllDistrict()
        spinnerProvince.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                (presenter as FilterPresenter).getDistrict(position)
            }
            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        })
        onClick()
        apply.setOnClickListener({
            startActivity(Intent(this, PostFragment::class.java))
        })
        val formatter = DecimalFormat("#,###,###")
        txtMinPrice.text = formatter.format(0) + " VNĐ"
        txtMaxPrice.text = formatter.format(12000*1000) + " VNĐ"
        seekbar.setValue(0F, 12000F)
        seekbar.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onRangeChanged(view: RangeSeekBar, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                txtMinPrice.text = formatter.format(leftValue.toInt()*1000) + " VNĐ"
                txtMaxPrice.text = formatter.format(rightValue.toInt()*1000) + " VNĐ"
                min_price = (leftValue.toInt()).toFloat()/1000
                max_price = (rightValue.toInt()).toFloat()/1000
            }
            override fun onStartTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {

            }

            override fun onStopTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {
            }
        })
        apply.setOnClickListener({getPost()})
        onClick()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.overridePendingTransition(0,R.anim.back_right)
        finish()
    }
    override fun getPost() {
        val type: Int = when {
            types[0] -> 0
            types[1] -> 1
            else -> 2
        }
        //val utils: ArrayList<String> = ArrayList()
        val city = spinnerProvince.text.toString()
        val district = spinnerDistrict.text.toString()
        val nameOfUtils: ArrayList<String> = arrayListOf("Máy lạnh", "Máy giặt", "Tủ lạnh", "WC riêng", "Chổ để xe",
                "Wifi", "Giờ giấc tự do", "Không chung chủ", "Bếp", "Giường ngủ",
                "Tivi", "Tủ quần áo", "Gác lửng", "Camera", "Bảo vệ", "Thú cưng")
//        var count = 0
//        for (i in 0 until check_utils.size) {
//            if (check_utils[i]) {
//                //val myUtils = RequestBody.create(MediaType.parse("text/plain"), nameOfUtils[i])
//                utils.add(count, nameOfUtils[i])
//                count++
//            }
//        }
        presenter?.actionSearch(city, district, min_price, max_price, type)
    }
    override fun onClick() {
        chip_compound.setOnClickListener({
            if(types[1]) {
                chip_compound.setChipBackgroundColorResource(R.color.background_chip)
                types[1]=!types[1]
            }
            else{
                chip_compound.setChipBackgroundColorResource(R.color.selected)
                types[1]=!types[1]
                if(types[0]) {
                    chip_rent.setChipBackgroundColorResource(R.color.background_chip)
                    types[0]=!types[0]
                }
            }
        })

        chip_rent.setOnClickListener({
            if(types[0]) {
                chip_rent.setChipBackgroundColorResource(R.color.background_chip)
                types[0]=!types[0]
            }
            else{
                chip_rent.setChipBackgroundColorResource(R.color.selected)
                types[0]=!types[0]
                if(types[1]) {
                    chip_compound.setChipBackgroundColorResource(R.color.background_chip)
                    types[1]=!types[1]
                }
            }
        })
        air_conditioner.setOnClickListener({
            if(check_utils[0]) {
                air_conditioner.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[0]=!check_utils[0]
            }
            else{
                air_conditioner.setChipBackgroundColorResource(R.color.selected)
                check_utils[0]=!check_utils[0]
            }
        })
        washing.setOnClickListener({
            if(check_utils[1]) {
                washing.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[1]=!check_utils[1]
            }
            else{
                washing.setChipBackgroundColorResource(R.color.selected)
                check_utils[1]=!check_utils[1]
            }
        })
        fridge.setOnClickListener({
            if(check_utils[2]) {
                fridge.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[2]=!check_utils[2]
            }
            else{
                fridge.setChipBackgroundColorResource(R.color.selected)
                check_utils[2]=!check_utils[2]
            }
        })
        wc.setOnClickListener({
            if(check_utils[3]) {
                wc.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[3]=!check_utils[3]
            }
            else{
                wc.setChipBackgroundColorResource(R.color.selected)
                check_utils[3]=!check_utils[3]
            }
        })
        parking.setOnClickListener({
            if(check_utils[4]) {
                parking.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[4]=!check_utils[4]
            }
            else{
                parking.setChipBackgroundColorResource(R.color.selected)
                check_utils[4]=!check_utils[4]
            }
        })
        wifi.setOnClickListener({
            if(check_utils[5]) {
                wifi.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[5]=!check_utils[5]
            }
            else{
                wifi.setChipBackgroundColorResource(R.color.selected)
                check_utils[5]=!check_utils[5]
            }
        })
        free.setOnClickListener({
            if(check_utils[6]) {
                free.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[6]=!check_utils[6]
            }
            else{
                free.setChipBackgroundColorResource(R.color.selected)
                check_utils[6]=!check_utils[6]
            }
        })
        key.setOnClickListener({
            if(check_utils[7]) {
                key.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[7]=!check_utils[7]
            }
            else{
                key.setChipBackgroundColorResource(R.color.selected)
                check_utils[7]=!check_utils[7]
            }
        })
        kitchen.setOnClickListener({
            if(check_utils[8]) {
                kitchen.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[8]=!check_utils[8]
            }
            else{
                kitchen.setChipBackgroundColorResource(R.color.selected)
                check_utils[8]=!check_utils[8]
            }
        })
        bed.setOnClickListener({
            if(check_utils[9]) {
                bed.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[9]=!check_utils[9]
            }
            else{
                bed.setChipBackgroundColorResource(R.color.selected)
                check_utils[9]=!check_utils[9]
            }
        })
        television.setOnClickListener({
            if(check_utils[10]) {
                television.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[10]=!check_utils[10]
            }
            else{
                television.setChipBackgroundColorResource(R.color.selected)
                check_utils[10]=!check_utils[10]
            }
        })
        closet.setOnClickListener({
            if(check_utils[11]) {
                closet.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[11]=!check_utils[11]
            }
            else{
                closet.setChipBackgroundColorResource(R.color.selected)
                check_utils[11]=!check_utils[11]
            }
        })
        mezzanine.setOnClickListener({
            if(check_utils[12]) {
                mezzanine.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[12]=!check_utils[12]
            }
            else{
                mezzanine.setChipBackgroundColorResource(R.color.selected)
                check_utils[12]=!check_utils[12]
            }
        })
        camera.setOnClickListener({
            if(check_utils[13]) {
                camera.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[13]=!check_utils[13]
            }
            else{
                camera.setChipBackgroundColorResource(R.color.selected)
                check_utils[13]=!check_utils[13]
            }
        })
        security_man.setOnClickListener({
            if(check_utils[14]) {
                security_man.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[14]=!check_utils[14]
            }
            else{
                security_man.setChipBackgroundColorResource(R.color.selected)
                check_utils[14]=!check_utils[14]
            }
        })
        pet.setOnClickListener({
            if(check_utils[15]) {
                pet.setChipBackgroundColorResource(R.color.background_chip)
                check_utils[15]=!check_utils[15]
            }
            else{
                pet.setChipBackgroundColorResource(R.color.selected)
                check_utils[15]=!check_utils[15]
            }
        })



    }

    override fun showNotification(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: FilterContract.Presenter) {
        this.presenter = presenter
    }
    override fun show(isShow: Boolean) {
        layout_filter.visibility = if (isShow) View.VISIBLE else View.GONE
    }
    override fun onResponse(posts: ArrayList<OverviewPost>?) {
        val intent = Intent(this, ListPostActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelableArrayList("post",posts)
        intent.putExtra("myBundle",bundle)
        val options = ActivityOptions.makeCustomAnimation(this, R.anim.right_to_left, 0)
        startActivity(intent,options.toBundle())
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setSpinnerDistrict(districts: LinkedList<String>){
        spinnerDistrict.attachDataSource(districts)
    }
}
