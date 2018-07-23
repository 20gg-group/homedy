@file:Suppress("DEPRECATION")

package gggroup.com.baron

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import gggroup.com.baron.adapter.ImageAdapter
import kotlinx.android.synthetic.main.activity_post.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager






class PostActivity : AppCompatActivity() {
    private var mAdapter: ImageAdapter? = null
    private var images: ArrayList<Image> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        upload_picture.setOnClickListener({
            getImage()
        })
    }
    private fun getImage() {
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
            printImages(images)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun printImages(images: List<Image>?) {
        if (images == null) return
        mAdapter = ImageAdapter(images)
        recycler_view.adapter = mAdapter
        val gridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recycler_view.layoutManager = gridLayoutManager
    }
}
