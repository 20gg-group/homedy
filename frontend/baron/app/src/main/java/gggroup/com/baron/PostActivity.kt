package gggroup.com.baron

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import android.net.Uri
import kotlinx.android.synthetic.main.activity_post.*
import java.io.File


class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        button.setOnClickListener({
            getImage()
        })
    }

    @Suppress("DEPRECATION")
    private fun getImage() {
        val imagePicker = ImagePicker.create(this)
                .language("in") // Set image picker language
                .toolbarArrowColor(resources.getColor(R.color.colorAccent)) // set toolbar arrow up color
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarDoneButtonText("DONE") // done button text
        imagePicker.multi()
                .limit(10) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
                .imageFullDirectory(Environment.getExternalStorageDirectory().path) // can be full path
                .start() // start image picker activity with request code
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val images = ImagePicker.getImages(data) as ArrayList<Image>
            printImages(images)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun printImages(images: List<Image>?) {
        if (images == null) return
        //val uploadPictureURI: List<String> = ArrayList()
//        var i = 0
//        val j = images.size
//        while (i < j){
//            val filePath = Uri.fromFile(File(uploadPictureURI[i]))
//            i++
//        }
        imageView.setImageURI(Uri.fromFile(File(images[0].path)))
        //Glide.with(this).load(selectedImage).into(imageView)
    }
}
