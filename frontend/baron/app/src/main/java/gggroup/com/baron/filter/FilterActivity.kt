@file:Suppress("DEPRECATION")
package gggroup.com.baron.filter

import android.os.Bundle
import android.renderscript.ScriptIntrinsicBlur
import android.support.v7.app.AppCompatActivity
import gggroup.com.baron.R
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isCamera = true
        setContentView(R.layout.activity_filter)
        camera.setOnClickListener({
            //            val whiteSpannable = SpannableString(chip_man.chipText)
//            val color = resources.getColor(R.color.backgroundColor)
//            val builder = whiteSpannable.setSpan(ForegroundColorSpan(color), 0, chip_man.chipText!!.length, 0)
//            chip_man.chipText = builder.toString()
            isCamera = if(isCamera) {
                camera.setChipBackgroundColorResource(R.color.colorPrimaryDark)
                !isCamera
            } else {
                camera.setChipBackgroundColorResource(R.color.lightMainColor)
                !isCamera
            }
        })
        customSeek()
    }
    private fun customSeek(){
        seekbar.setValue(100F, 1000F)

    }

}