@file:Suppress("DEPRECATION")

package gggroup.com.baron.main.post

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.design.button.MaterialButton
import android.support.v4.app.Fragment
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import gggroup.com.baron.adapter.ImageAdapter
import kotlinx.android.synthetic.main.fragment_post.*
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import gggroup.com.baron.R
import java.util.*
import java.util.Arrays.asList
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import org.angmarch.views.NiceSpinner


class PostFragment : Fragment(), PostContract.View {
    private var presenter: PostContract.Presenter? = null
    private var mAdapter: ImageAdapter? = null
    private var images: ArrayList<Image> = ArrayList()
    private var types: BooleanArray = booleanArrayOf(false,false)
    private var utils: BooleanArray = booleanArrayOf(false,false,false,false,
                                                    false,false,false,false,
                                                    false,false,false,false,
                                                    false,false,false,false)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post, null)
        presenter = PostPresenter(this)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        upload_picture.setOnClickListener({
            getImage()
        })
        val sex = LinkedList(asList("Nam", "Nữ", "Cả 2"))
        spinnerSex.attachDataSource(sex)
        val city = LinkedList(asList("Hà Nội", "Hồ Chí Minh"))
        spinnerProvince.attachDataSource(city)
        presenter?.getAllDistrict()
        spinnerProvince.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                (presenter as PostPresenter).getDistrict(position)
            }
            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        })
        getUtils()
        getType()
    }
    override fun getType(){
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
    override fun getImage() {
        val imagePicker = ImagePicker.create(this)
                .language("in") // Set image picker language
                .toolbarArrowColor(resources.getColor(R.color.colorAccent)) // set toolbar arrow up color
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarDoneButtonText("DONE") // done button text
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

    override fun setPresenter(presenter: PostContract.Presenter) {
        this.presenter = presenter
    }
    override fun show(isShow: Boolean) {
        layout_post.visibility = if (isShow) View.VISIBLE else View.GONE
        progress_bar.visibility = if (isShow) View.GONE else View.VISIBLE
    }
    override fun onResponse(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setSpinnerDistrict(districts: LinkedList<String>){
        spinnerDistrict.attachDataSource(districts)
    }
}
