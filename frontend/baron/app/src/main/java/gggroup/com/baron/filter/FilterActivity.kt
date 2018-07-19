package gggroup.com.baron.filter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import gggroup.com.baron.R
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {
    var list_of_item= arrayOf("Phòng trọ,nhà trọ","nhà thuê nguyên căn","tìm bạn ở ghép")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        val adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,list_of_item)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.adapter=adapter
        // Set an on item selected listener for spinner object
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
              //  text_view.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                Toast.makeText(this@FilterActivity,list_of_item[position],Toast.LENGTH_SHORT)
            }
            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

    }
}
