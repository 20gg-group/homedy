@file:Suppress("DEPRECATION")

package gggroup.com.baron.filter

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
import java.text.DecimalFormat


class FilterActivity : AppCompatActivity(),FilterContract.View {
    private var presenter: FilterContract.Presenter? = null
    private var types: BooleanArray = booleanArrayOf(false,false)
    private var utils: BooleanArray = booleanArrayOf(false,false,false,false,
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
            //this.overridePendingTransition(0,R.anim.back_right)
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
        getUtils()
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
            }
            override fun onStartTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {

            }

            override fun onStopTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {

            }
        })
        getType()
    }
    private fun getType(){
        chip_compound.setOnClickListener({
            if(types[0]) {
                chip_compound.setChipBackgroundColorResource(R.color.background_chip)
                types[0]=!types[0]
            }
            else{
                chip_compound.setChipBackgroundColorResource(R.color.selected)
                types[0]=!types[0]
                if(types[1]) {
                    chip_rent.setChipBackgroundColorResource(R.color.background_chip)
                    types[1]=!types[1]
                }
            }
        })

        chip_rent.setOnClickListener({
            if(types[1]) {
                chip_rent.setChipBackgroundColorResource(R.color.background_chip)
                types[1]=!types[1]
            }
            else{
                chip_rent.setChipBackgroundColorResource(R.color.selected)
                types[1]=!types[1]
                if(types[0]) {
                    chip_compound.setChipBackgroundColorResource(R.color.background_chip)
                    types[0]=!types[0]
                }
            }
        })
    }
    override fun getUtils() {
        air_conditioner.setOnClickListener({
            if(utils[0]) {
                air_conditioner.setChipBackgroundColorResource(R.color.background_chip)
                utils[0]=!utils[0]
            }
            else{
                air_conditioner.setChipBackgroundColorResource(R.color.selected)
                utils[0]=!utils[0]
            }
        })
        washing.setOnClickListener({
            if(utils[1]) {
                washing.setChipBackgroundColorResource(R.color.background_chip)
                utils[1]=!utils[1]
            }
            else{
                washing.setChipBackgroundColorResource(R.color.selected)
                utils[1]=!utils[1]
            }
        })
        fridge.setOnClickListener({
            if(utils[2]) {
                fridge.setChipBackgroundColorResource(R.color.background_chip)
                utils[2]=!utils[2]
            }
            else{
                fridge.setChipBackgroundColorResource(R.color.selected)
                utils[2]=!utils[2]
            }
        })
        wc.setOnClickListener({
            if(utils[3]) {
                wc.setChipBackgroundColorResource(R.color.background_chip)
                utils[3]=!utils[3]
            }
            else{
                wc.setChipBackgroundColorResource(R.color.selected)
                utils[3]=!utils[3]
            }
        })
        parking.setOnClickListener({
            if(utils[4]) {
                parking.setChipBackgroundColorResource(R.color.background_chip)
                utils[4]=!utils[4]
            }
            else{
                parking.setChipBackgroundColorResource(R.color.selected)
                utils[4]=!utils[4]
            }
        })
        wifi.setOnClickListener({
            if(utils[5]) {
                wifi.setChipBackgroundColorResource(R.color.background_chip)
                utils[5]=!utils[5]
            }
            else{
                wifi.setChipBackgroundColorResource(R.color.selected)
                utils[5]=!utils[5]
            }
        })
        free.setOnClickListener({
            if(utils[6]) {
                free.setChipBackgroundColorResource(R.color.background_chip)
                utils[6]=!utils[6]
            }
            else{
                free.setChipBackgroundColorResource(R.color.selected)
                utils[6]=!utils[6]
            }
        })
        key.setOnClickListener({
            if(utils[7]) {
                key.setChipBackgroundColorResource(R.color.background_chip)
                utils[7]=!utils[7]
            }
            else{
                key.setChipBackgroundColorResource(R.color.selected)
                utils[7]=!utils[7]
            }
        })
        kitchen.setOnClickListener({
            if(utils[8]) {
                kitchen.setChipBackgroundColorResource(R.color.background_chip)
                utils[8]=!utils[8]
            }
            else{
                kitchen.setChipBackgroundColorResource(R.color.selected)
                utils[8]=!utils[8]
            }
        })
        bed.setOnClickListener({
            if(utils[9]) {
                bed.setChipBackgroundColorResource(R.color.background_chip)
                utils[9]=!utils[9]
            }
            else{
                bed.setChipBackgroundColorResource(R.color.selected)
                utils[9]=!utils[9]
            }
        })
        television.setOnClickListener({
            if(utils[10]) {
                television.setChipBackgroundColorResource(R.color.background_chip)
                utils[10]=!utils[10]
            }
            else{
                television.setChipBackgroundColorResource(R.color.selected)
                utils[10]=!utils[10]
            }
        })
        closet.setOnClickListener({
            if(utils[11]) {
                closet.setChipBackgroundColorResource(R.color.background_chip)
                utils[11]=!utils[11]
            }
            else{
                closet.setChipBackgroundColorResource(R.color.selected)
                utils[11]=!utils[11]
            }
        })
        mezzanine.setOnClickListener({
            if(utils[12]) {
                mezzanine.setChipBackgroundColorResource(R.color.background_chip)
                utils[12]=!utils[12]
            }
            else{
                mezzanine.setChipBackgroundColorResource(R.color.selected)
                utils[12]=!utils[12]
            }
        })
        camera.setOnClickListener({
            if(utils[13]) {
                camera.setChipBackgroundColorResource(R.color.background_chip)
                utils[13]=!utils[13]
            }
            else{
                camera.setChipBackgroundColorResource(R.color.selected)
                utils[13]=!utils[13]
            }
        })
        security_man.setOnClickListener({
            if(utils[14]) {
                security_man.setChipBackgroundColorResource(R.color.background_chip)
                utils[14]=!utils[14]
            }
            else{
                security_man.setChipBackgroundColorResource(R.color.selected)
                utils[14]=!utils[14]
            }
        })
        pet.setOnClickListener({
            if(utils[15]) {
                pet.setChipBackgroundColorResource(R.color.background_chip)
                utils[15]=!utils[15]
            }
            else{
                pet.setChipBackgroundColorResource(R.color.selected)
                utils[15]=!utils[15]
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
    override fun onResponse(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setSpinnerDistrict(districts: LinkedList<String>){
        spinnerDistrict.attachDataSource(districts)
    }
}
