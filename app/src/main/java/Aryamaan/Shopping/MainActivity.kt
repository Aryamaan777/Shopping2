package Aryamaan.Shopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    //var search_button: Button?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        search_button?.setOnClickListener {
            Log.d("MainActivity","XXXXX")
            var user_search= search.text.toString()
            Log.d("MainActivity",user_search)
            intent=Intent(this,SearchActivity::class.java)
            intent.putExtra("search",user_search)
            startActivity(intent)
        }

    }
}
