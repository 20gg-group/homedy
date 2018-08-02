@file:Suppress("DEPRECATION")

package gggroup.com.baron.main.post

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import gggroup.com.baron.adapter.ImageAdapter
import kotlinx.android.synthetic.main.fragment_post.*
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import gggroup.com.baron.R
import java.util.*
import java.util.Arrays.asList
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ProgressBar
import com.esafirm.imagepicker.features.ReturnMode
import gggroup.com.baron.main.MainActivity
import gggroup.com.baron.utils.HashMapUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.angmarch.views.NiceSpinner
import java.io.File
import kotlin.collections.ArrayList


class PostFragment : Fragment(), PostContract.View {
    private var presenter: PostContract.Presenter? = null
    private var mAdapter: ImageAdapter? = null
    private var images: ArrayList<Image> = ArrayList()
    private var types: BooleanArray = booleanArrayOf(false,false)
    private lateinit var spinnerDistrict: NiceSpinner
    private lateinit var layout: ConstraintLayout
    private lateinit var progress: ProgressBar
    private var hanoi: LinkedList<String> = LinkedList()
    private var hochiminh: LinkedList<String> = LinkedList()
    private var check_utils: BooleanArray = booleanArrayOf(false,false,false,false,
                                                    false,false,false,false,
                                                    false,false,false,false,
                                                    false,false,false,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post, null)
        spinnerDistrict = view.findViewById(R.id.spinnerDistrict)
        layout = view.findViewById(R.id.layout_post)
        progress = view.findViewById(R.id.progress_bar)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = PostPresenter(this)
        upload_picture.setOnClickListener({
            getImage()
        })

        val sex = arrayListOf("Nam", "Nữ", "Nam/Nữ")
        spinnerSex.attachDataSource(sex)
        val city = arrayListOf("Hà Nội", "Hồ Chí Minh")
        if(hanoi.size<1) {
            spinnerProvince.attachDataSource(city)
            presenter?.getAllDistrict()
        }
        else
        {   spinnerProvince.attachDataSource(city)
            if(spinnerProvince.text == "Hà Nội")
                spinnerDistrict.attachDataSource(hanoi)
            else
                spinnerDistrict.attachDataSource(hochiminh)
        }
        spinnerProvince.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                if(position == 0)
                    spinnerDistrict.attachDataSource(hanoi)
                else
                    spinnerDistrict.attachDataSource(hochiminh)
            }
            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        })
        onClick()
        post.setOnClickListener({
            post()
        })
    }
    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val item = menu?.findItem(R.id.action_filter)
        item?.isVisible = false
    }
    override fun onClick() {
        minus.setOnClickListener({
        var amount = amountPeople.text.toString().toInt()
        amount--
        amountPeople.text = amount.toString()
        if(amount == 1)
            minus.isEnabled = false
        if(amount == 9)
            plus.isEnabled = true
    })
        plus.setOnClickListener({
            var amount = amountPeople.text.toString().toInt()
            amount++
            if(amount==2)
                minus.isEnabled = true
            if(amount==10)
                plus.isEnabled = false
            amountPeople.text = amount.toString()
        })
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
    override fun getImage() {
        val imagePicker = ImagePicker.create(this)
                .returnMode(ReturnMode.NONE) // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
                .folderMode(true)
                .toolbarFolderTitle("Bộ sưu tập") // folder selection title
                .toolbarImageTitle("Chọn ảnh")
                .toolbarDoneButtonText("XONG") // done button text
        imagePicker.multi()
                .limit(10) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .origin(images) // original selected images, used in multi mode
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
                .imageFullDirectory(Environment.getExternalStorageDirectory().path) // can be full path
                .start() // start image picker activity with request code
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            images = ImagePicker.getImages(data) as ArrayList<Image>
            displayImg(images)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun displayImg(images: ArrayList<Image>?) {
        if (images == null) return
        mAdapter = ImageAdapter(images,context)
        recycler_view.adapter = mAdapter
        val gridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recycler_view.layoutManager = gridLayoutManager
        recycler_view.isNestedScrollingEnabled = false
    }

    override fun showNotification(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    override fun post() {
        when {
            edt_title.text.isEmpty() ->
            {
                showNotification("Vui lòng nhập tiêu đề bài đăng")
                edt_title.requestFocus()
            }
            edt_price.text.isEmpty() -> {
                showNotification("Vui lòng nhập giá phòng")
                edt_price.requestFocus()
            }
            edt_area.text.isEmpty() -> {
                showNotification("Vui lòng nhập diện tích phòng")
                edt_area.requestFocus()
            }
            edt_address.text.isEmpty() -> {
                showNotification("Vui lòng nhập số nhà, tên đường, tên phường")
                edt_address.requestFocus()
            }
            edt_phone.text.isEmpty() -> {
                showNotification("Vui lòng nhập số điện thoại")
                edt_phone.requestFocus()
            }
            images.size < 1 -> showNotification("Vui lòng thêm ảnh mô tả phòng")
            else -> {
                val title = edt_title.text.toString()
                val price = edt_price.text.toString().toFloat()
                val area = edt_area.text.toString().toFloat()
                val description = edt_describe.text.toString()
                val phone = edt_phone.text.toString()
                val type: Int = when {
                    types[0] -> 0
                    types[1] -> 1
                    else -> 2
                }
                val utils: ArrayList<String> = ArrayList()
                val city = spinnerProvince.text.toString()
                val district = spinnerDistrict.text.toString()
                val address = edt_address.text.toString()
                val listFile: ArrayList<File>? = ArrayList()
                if (images.size > 0) {
                    for (i in 0 until images.size)
                        listFile?.add(File(images[0].path))
                }
                val nameOfUtils: ArrayList<String> = arrayListOf("Máy lạnh", "Máy giặt", "Tủ lạnh", "WC riêng", "Chổ để xe",
                        "Wifi", "Giờ giấc tự do", "Không chung chủ", "Bếp", "Giường ngủ",
                        "Tivi", "Tủ quần áo", "Gác lửng", "Camera", "Bảo vệ", "Thú cưng")
                var count = 0
                for (i in 0 until check_utils.size) {
                    if (check_utils[i]) {
                        //val myUtils = RequestBody.create(MediaType.parse("text/plain"), nameOfUtils[i])
                        utils.add(count, nameOfUtils[i])
                        count++
                    }
                }
                val sex = spinnerSex.selectedIndex
                val quantity = amountPeople.text.toString().toInt()
                presenter?.post(title, price, area, description, phone,
                        type,sex,quantity, utils, city, district, address, listFile)
            }
        }
    }
    override fun setPresenter(presenter: PostContract.Presenter) {
        this.presenter = presenter
    }

    override fun show(isShow: Boolean) {
        layout.visibility = if (isShow) View.VISIBLE else View.GONE
        progress.visibility = if (isShow) View.GONE else View.VISIBLE
    }

    override fun onResponse(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun returnDistrict(listHaNoi: LinkedList<String>,listHoChiMinh: LinkedList<String>){
        //this.districts = districts
        hanoi = listHaNoi
        hochiminh = listHoChiMinh
        spinnerDistrict.attachDataSource(hanoi)
    }

    override fun isPost(isPost: Boolean) {
        progress.visibility = if (isPost) View.VISIBLE else View.GONE
        post.isEnabled = !isPost
    }
}
